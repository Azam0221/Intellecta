package com.example.intellecta.worker

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.intellecta.dao.FileDao
import com.example.intellecta.dao.NoteDao
import com.example.intellecta.data.TokenManager
import com.example.intellecta.network.ApiService
import com.example.intellecta.network.syncModels.FileSyncRequest
import com.example.intellecta.network.syncModels.NoteSyncRequest
import com.example.intellecta.repository.FileStorageRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


class SyncNotesWorker(
    context: Context,
    params: WorkerParameters,
    private val apiService: ApiService,
    private val noteDao: NoteDao,
    private val fileDao: FileDao,
    private val fileStorageRepository: FileStorageRepository,
    private val tokenManager: TokenManager,
    private val supabase : SupabaseClient
): CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "SyncNotesWorked"

        private val SEVEN_DAYS_MS = TimeUnit.DAYS.toMillis(7)
    }


    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {

        var notenoteFailCount = 0
        var fileFailCount = 0

        try {
            Log.e(TAG, "DO Work started")
            val token = tokenManager.getTokenAsync(true)
            if (token == null) {
                Log.e(TAG, "Token refresh failed or user is not logged in. Retrying.")
                return@withContext Result.retry()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Token refresh failed with exception. Retrying.", e)
            return@withContext Result.retry()
        }


        try {
            Log.d(TAG, "Sync worker starting...")
            var noteFailCount = 0


            /**
             ************* UPLOAD NOTES THROUGH BACKEND  *************
             **/

            val unsyncedNotes = noteDao.getUnsyncedNotes()
            if(unsyncedNotes.isEmpty()){
                Log.d(TAG,"No notes to create/update")

            }else {
                Log.d(TAG, "Found ${unsyncedNotes.size} notes to create/update.")
            }

            for( note in unsyncedNotes){
                try {

                    val noteRequest = NoteSyncRequest(
                        title = note.title,
                        content = note.content,
                        summary = note.summary,
                        category = note.category,
                        timestamp = note.timeStamp,
                    )

                    val syncTime = System.currentTimeMillis()
                    val backendNoteId: String

                    if (note.servedId == null) {
                        Log.d(TAG, "Creating new note: ${note.title}")
                        val response = apiService.syncNotes(noteRequest)

                        if (response.isSuccessful && response.body() != null) {
                            backendNoteId = response.body()!!.id
                            noteDao.markNotesAsSynced(note.id, backendNoteId, syncTime)
                            Log.d(
                                TAG,
                                "Note created. Local ID: ${note.id} -> Server ID: $backendNoteId"
                            )
                        } else {
                            throw Exception("Failed to create note 1: ${response.message()}")
                        }

                    } else {
                        Log.d(TAG, "Updating existing note: ${note.title}")
                        val response = apiService.updateNotes(note.servedId!!, noteRequest)

                        if (response.isSuccessful) {
                            backendNoteId = note.servedId!! // ID doesn't change
                            noteDao.markNotesAsSynced(note.id, backendNoteId, syncTime)
                            Log.d(TAG, "Note updated. Server ID: $backendNoteId")
                        } else {
                            throw Exception("Failed to update note: ${response.message()}")
                        }
                    }
                }
                catch (e:Exception){
                    noteFailCount++
                    Log.e(TAG, "Error syncing note: ${note.title}", e)
                }
            }


            /**
             ************* DELETE NOTES  *************
             **/


            val deletedNotes = noteDao.getDeletedUnSyncedNotes()
            if(deletedNotes.isEmpty()){
                Log.d(TAG,"No notes to delete")
            }
            else{
                Log.d(TAG,"${deletedNotes.size} notes to be deleted")
            }

            for(note in deletedNotes){
                try {
                    val response = apiService.deleteNote(note.servedId!!)

                    if(response.isSuccessful){
                        Log.d(TAG,"${response.body()}")
                        Log.d(TAG,"Note deleted successfully")
                    }
                    else{
                        throw Exception("Failed to update note: ${response.message()}")
                    }
                }catch (e:Exception){
                    noteFailCount++
                    Log.e(TAG, "Error syncing note: ${note.title}", e)
                }
            }


            /**
             ************* File Upload  *************
             **/


            val unsyncedFiles = fileDao.getUnsyncedFiles()
            Log.e(TAG, "Found Unsynced Files: ${unsyncedFiles.size}")

            for(fileMeta in unsyncedFiles){
                try {
                    if(fileMeta.servedId == null){
                        Log.w(TAG, "Skipping file ${fileMeta.fileName}, parent note is not synced.")
                        continue
                    }

                    // get the local files first
                   val localFile = fileStorageRepository.getFile(fileMeta.fileData)
                    if(!localFile.exists()){
                        Log.e(TAG, "Local file not found, skipping: ${fileMeta.fileData}")
                        noteFailCount++
                        continue
                    }

                        // Reading the file
                    val fileBytes = localFile.readBytes()

                    val storagePath = "user-files/${fileMeta.servedId}/${fileMeta.fileName}"
                    Log.d(TAG, "Uploading $storagePath to Supabase...")

                    val uploadResponse = supabase.storage
                        .from("intellecta-files") // Your bucket name
                        .upload(storagePath, fileBytes, {
                            upsert = true
                        })

                    val supabaseUrl = supabase.storage
                        .from("intellecta-files")
                        .publicUrl(storagePath)


                    // Send the meta data to backend
                    val fileRequest = FileSyncRequest(
                        servedId = fileMeta.servedId!!,
                        fileName = fileMeta.fileName,
                        fileType = fileMeta.fileType,
                        supabaseUrl = supabaseUrl
                    )

                    val response = apiService.syncFiles(fileRequest)
                    if (response.isSuccessful && response.body() != null){
                        val backendFileId = response.body()!!.id
                        val syncTime = System.currentTimeMillis()
                        fileDao.markFileAsSynced(fileMeta.id,backendFileId,syncTime)
                        Log.d(TAG,"Files synced successfully")

                    }
                    else{
                        throw Exception("Failed to sync file metadata: ${response.message()}")
                    }

                }
                catch (e:Exception){
                    fileFailCount++
                    Log.e(TAG, "Error syncing file: ${fileMeta.fileName}", e)
                }
            }


            /**
             ********** DELETE FILES *********
              **/


            val deletedFiles = fileDao.getDeletedUnsyncedFiles()
            for (fileMeta in deletedFiles) {
                try {
                    if (fileMeta.servedId == null) {
                        fileDao.hardDeleteFile(fileMeta.id)
                        continue
                    }

                    val response = apiService.deleteFile(fileMeta.servedId!!)
                    if (response.isSuccessful) {
                        fileDao.hardDeleteFile(fileMeta.id)
                        Log.d(TAG, "File deleted: ${fileMeta.fileName}")
                    } else {
                        throw Exception("Failed to delete file: ${response.message()}")
                    }
                } catch (e: Exception) {
                    fileFailCount++
                    Log.e(TAG, "Error deleting file: ${fileMeta.fileName}", e)
                }
            }

            // Delete files



            Log.d(TAG, "Sync complete. Total failures: $noteFailCount")
            return@withContext if (noteFailCount == 0) Result.success() else Result.retry()
        }
        catch (e:Exception){
            Log.e(TAG, "Sync worker failed", e)
            return@withContext Result.failure()
        }

    }


//    private suspend fun cleanupOldDeletions() {
//        val sevenDaysAgo = System.currentTimeMillis() - SEVEN_DAYS_MS
//        try {
//            Log.d(TAG, "Cleaning up old deletions before $sevenDaysAgo")
//            val notesDeleted = noteDao.hardDeleteOldSyncedNotes(sevenDaysAgo)
//            val filesDeleted = fileDao.hardDeleteOldSyncedFiles(sevenDaysAgo)
//            Log.d(TAG, "Cleanup complete. Removed $notesDeleted old notes and $filesDeleted old files.")
//        } catch (e: Exception) {
//            Log.e(TAG, "Failed to cleanup old deletions", e)
//        }
//    }

//    private suspend fun syncDeletions(): Int {
//        var noteFailCount = 0
//        val syncTime = System.currentTimeMillis()
//
//        // 1. Delete Files
//        val deletedFiles = fileDao.getDeletedUnSyncedNotes()
//        Log.d(TAG, "Found ${deletedFiles.size} files to delete from server.")
//        for (file in deletedFiles) {
//            try {
//                if (file.servedId == null) {
//                    // Deleted before it was ever synced. Just delete locally.
//                    fileDao.hardDeleteSyncedNoteById(file.id)
//                } else {
//                    // Tell the server to delete it
//                    val response = apiService.deleteFile(file.servedId!!)
//                    if (response.isSuccessful) {
//                        // Mark as synced. It will be cleaned up in 7 days.
//                        fileDao.markNotesAsSynced(file.id,file.servedId!!,syncTime)
//                    } else {
//                        throw Exception("Failed to delete file: ${response.message()}")
//                    }
//                }
//            } catch (e: Exception) {
//                noteFailCount++
//                Log.e(TAG, "Failed to sync file deletion: ${file.fileName}", e)
//              //  fileDao.markFileSyncFailed(file.id, e.message ?: "Delete failed")
//            }
//        }
//
//        // 2. Delete Notes
//        val deletedNotes = noteDao.getDeletedUnSyncedNotes()
//        Log.d(TAG, "Found ${deletedNotes.size} notes to delete from server.")
//        for (note in deletedNotes) {
//            try {
//                if (note.servedId == null) {
//                    // Deleted before it was ever synced. Just delete locally.
//                    noteDao.hardDeleteSyncedNoteById(note.id)
//                } else {
//                    val response = apiService.deleteNote(note.servedId!!)
//                    if (response.isSuccessful) {
//                        // Mark as synced. It will be cleaned up in 7 days.
//                        noteDao.markNotesAsSynced(note.id, note.servedId!!, syncTime)
//                    } else {
//                        throw Exception("Failed to delete note: ${response.message()}")
//                    }
//                }
//            } catch (e: Exception) {
//                noteFailCount++
//                Log.e(TAG, "Failed to sync note deletion: ${note.title}", e)
//               // noteDao.markNoteSyncFailed(note.id, e.message ?: "Delete failed")
//            }
//        }
//
//        return noteFailCount
//    }

    /**
     * Syncs all unsynced files for a specific note (CREATE-ONLY).
     */
//    private suspend fun syncFilesForNote(localNoteId: Int, backendNoteId: String): Int {
//        var noteFailCount = 0
//        val unsyncedFiles = fileDao.getUnsyncedFilesForNote(localNoteId)
//        if (unsyncedFiles.isEmpty()) return 0
//
//        Log.d(TAG, "Found ${unsyncedFiles.size} files for note ID $localNoteId")
//
//        for (file in unsyncedFiles) {
//            try {
//                if (file.servedId != null) {
//                    // This file is already synced. This should not happen
//                    // if getUnsyncedFilesForNote is correct, but as a safeguard.
//                    Log.w(TAG, "Skipping file ${file.fileName}, already has server ID.")
//                    continue
//                }
//
//                val fileContent = readFileAsBase64(file.fileData)
//                if (fileContent.isEmpty()) {
//                    Log.w(TAG, "Skipping empty file: ${file.fileName}")
//                    continue
//                }
//
//                val fileRequest = FileSyncRequest(
//                    noteId = backendNoteId, // Use the server's note ID
//                    fileName = file.fileName,
//                    fileType = file.fileType,
//                    fileData = fileContent,
//                    localNoteId = localNoteId.toString(),
//                    localFileId = file.id.toString()
//                )
//
//                val fileResponse = apiService.syncFiles(fileRequest) // POST
//
//                if (fileResponse.isSuccessful && fileResponse.body() != null) {
//                    val backendFileId = fileResponse.body()!!.id
//                    val syncTime = System.currentTimeMillis()
//                    fileDao.markFileSynced(file.id, backendFileId, syncTime)
//                    Log.d(TAG, "File synced: ${file.fileName} -> $backendFileId")
//                } else {
//                    throw Exception("Failed to sync file: ${fileResponse.message()}")
//                }
//            } catch (e: Exception) {
//                noteFailCount++
//                Log.e(TAG, "Failed to sync file: ${file.fileName}", e)
//                fileDao.markFileSyncFailed(file.id, e.message ?: "Unknown file error")
//            }
//        }
//        return noteFailCount
//    }
//




    private fun readFileAsBase64(filePath: String): String {
        return try {
           val file = fileStorageRepository.getFile(filePath)
            if (file.exists()) {
                val bytes = file.readBytes()
                Base64.encodeToString(bytes, Base64.NO_WRAP)
            } else {
                Log.d("SyncWorked", "File is not able to read")
                ""
            }
        } catch (e: Exception) {
            Log.e("SyncWorker", "Error reading file: $filePath", e)
            ""
        }
    }
}
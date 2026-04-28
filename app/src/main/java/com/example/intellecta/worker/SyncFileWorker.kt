package com.example.intellecta.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.intellecta.dao.FileDao
import com.example.intellecta.dao.NoteDao
import com.example.intellecta.model.request.FileSoftDeleteRequest
import com.example.intellecta.model.request.FileSupabaseRequest
import com.example.intellecta.network.syncModels.FileSyncRequest
import com.example.intellecta.repository.FileStorageRepository
import com.example.intellecta.supabase_config.SupabaseConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable


class SyncFileWorker(
    context: Context,
    params: WorkerParameters,
    private val supabase: SupabaseClient,
    private val noteDao: NoteDao,
    private val fileDao: FileDao,
    private val fileStorageRepository: FileStorageRepository,
) : CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "SyncFileWorker"
        private const val BUCKET = "intellecta-files"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            // check user is logged in
            val userId = supabase.auth.currentUserOrNull()?.id
            if (userId == null) {
                Log.e(TAG, "User not logged in, retrying")
                return@withContext Result.retry()
            }

            var failCount = 0

            /**
             * UPLOAD FILES
             */
            val unsyncedFiles = fileDao.getUnsyncedFiles()
            Log.d(TAG, "Found ${unsyncedFiles.size} files to sync")

            for (fileMeta in unsyncedFiles) {
                try {
                    // parent note must be synced first
                    val parentNote = noteDao.getNote(fileMeta.noteId)
                    if (parentNote?.servedId == null) {
                        Log.w(TAG, "Skipping ${fileMeta.fileName} — parent note not synced yet")
                        continue
                    }

                    val parentNoteServedId = parentNote.servedId!!

                    // read local file
                    val localFile = fileStorageRepository.getFile(fileMeta.fileData)
                    if (!localFile.exists()) {
                        Log.w(TAG, "Local file not found: ${fileMeta.fileData}")
                        failCount++
                        continue
                    }

                    val fileBytes = localFile.readBytes()
                    val storagePath = "$userId/${parentNoteServedId}/${fileMeta.fileName}"

                    // upload to Supabase Storage

                    supabase.storage
                        .from(BUCKET)
                        .upload(storagePath, fileBytes, upsert = true)

                    val publicUrl = supabase.storage
                        .from(BUCKET)
                        .publicUrl(storagePath)

                    Log.d(TAG, "Uploaded to Supabase Storage: $publicUrl")

                    val syncTime = System.currentTimeMillis()

                    // save file metadata to Supabase DB
                    val response = supabase.postgrest
                        .from("files")
                        .insert(
                            FileSupabaseRequest(
                                local_id = fileMeta.id,
                                note_id = parentNoteServedId,
                                user_id = userId,
                                file_name = fileMeta.fileName,
                                file_type = fileMeta.fileType,
                                supabase_url = publicUrl,
                                is_deleted = false,
                                last_modified = syncTime
                            )
                        ) {
                            select(Columns.raw("id"))
                        }
                        .decodeSingle<Map<String, String>>()

                    val supabaseFileId = response["id"]!!
                    fileDao.markFileAsSynced(fileMeta.id, supabaseFileId, syncTime)
                    Log.d(TAG, "File synced: ${fileMeta.fileName} → $supabaseFileId")

                } catch (e: Exception) {
                    failCount++
                    Log.e(TAG, "Error syncing file: ${fileMeta.fileName}", e)
                }
            }

            /**
             * DELETE FILES
             */

            fileDao.hardDeleteUnsyncedDeletedFiles()
            Log.d(TAG,"All unsynced files got delete OOPS")

            val deletedFiles = fileDao.getDeletedUnsyncedFiles()
            Log.d(TAG, "Found ${deletedFiles.size} files to delete")

            for (fileMeta in deletedFiles) {
                try {
                    if (fileMeta.servedId == null) {
                        // never synced, just hard delete locally
                        fileDao.hardDeleteFile(fileMeta.id)
                        continue
                    }
                    val parentNote = noteDao.getNote(fileMeta.noteId)
                    val userId = supabase.auth.currentUserOrNull()?.id

                    if (userId != null && parentNote?.servedId != null) {
                        val storagePath = "$userId/${parentNote.servedId}/${fileMeta.fileName}"
                        try {
                            supabase.storage
                                .from(BUCKET)
                                .delete(listOf(storagePath))
                            Log.d(TAG, "File permanently deleted from Storage: $storagePath")
                        } catch (e: Exception) {
                            Log.w(TAG, "Storage delete failed for ${fileMeta.fileName} — may not exist")
                        }
                    }

                    // soft delete on Supabase DB
                    supabase.postgrest
                        .from("files")
                        .update(FileSoftDeleteRequest(is_deleted = true)) {
                            filter { eq("id", fileMeta.servedId!!) }
                        }

                    fileDao.softDeleteFile(fileMeta.id)
                    Log.d(TAG, "File deleted: ${fileMeta.fileName}")

                } catch (e: Exception) {
                    failCount++
                    Log.e(TAG, "Error deleting file: ${fileMeta.fileName}", e)
                }
            }

            Log.d(TAG, "File sync complete. Failures: $failCount")
            return@withContext if (failCount == 0) Result.success() else Result.retry()

        } catch (e: Exception) {
            Log.e(TAG, "File sync worker crashed", e)
            return@withContext Result.failure()
        }
    }
}
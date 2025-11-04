package com.example.intellecta.worker

import android.content.Context
import android.content.ContextParams
import android.util.Base64
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.intellecta.dao.FileDao
import com.example.intellecta.dao.IntellectaDatabase
import com.example.intellecta.dao.NoteDao
import com.example.intellecta.fileManaging.FileManager
import com.example.intellecta.network.ApiService
import com.example.intellecta.network.syncModels.FileSyncRequest
import com.example.intellecta.network.syncModels.NoteSyncRequest
import com.example.intellecta.repository.FileStorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject

class SyncNotesWorker(
    context: Context,
    params: WorkerParameters,

    private val apiService: ApiService,
    private val noteDao: NoteDao,
    private val fileDao: FileDao,
    private val fileStorageRepository: FileStorageRepository
) : CoroutineWorker(context, params) {


    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            Log.d("SyncWorker", "Starting sync...")

            // Get all notes that haven't been synced
            val unsyncedNotes = noteDao.getUnsyncedNotes()

            if (unsyncedNotes.isEmpty()) {
                Log.d("SyncWorker", "No notes to sync")
                return@withContext Result.success()
            }

            Log.d("SyncWorker", "Found ${unsyncedNotes.size} notes to sync")

            var successCount = 0
            var failCount = 0

            // Sync each note
            for (note in unsyncedNotes) {
                var allFilesSyncedSuccessfully = true
                try {
                    // 1. Sync the note first
                    val noteRequest = NoteSyncRequest(
                        title = note.title,
                        content = note.content,
                        summary = note.summary,
                        category = note.category,
                        timestamp = note.timeStamp
                    )

                    val noteResponse = apiService.syncNotes(noteRequest)

                    if (noteResponse.isSuccessful && noteResponse.body() != null) {
                        val backendNoteId = noteResponse.body()!!.id
                        val syncTime = System.currentTimeMillis()

                        // Mark note as synced in Room
                        noteDao.markNotesAsSynced(note.id, backendNoteId, syncTime)

                        Log.d("SyncWorker", "Note synced: ${note.title}")

                        // 2. Sync associated files
                        val unsyncedFiles = fileDao.getUnsyncedFilesForNote(note.id)

                        for (file in unsyncedFiles) {
                            try {
                                // Read file and convert to Base64
                                val fileContent = readFileAsBase64(file.fileData)
                                if (fileContent.isEmpty()) {
                                    Log.w("SyncWorker", "Skipping empty file: ${file.fileName}")
                                    continue // Don't try to sync an empty file
                                }

                                val fileRequest = FileSyncRequest(
                                    noteId = backendNoteId,
                                    fileName = file.fileName,
                                    fileType = file.fileType,
                                    fileData = fileContent
                                )

                                val fileResponse = apiService.syncFiles(fileRequest)

                                if (fileResponse.isSuccessful && fileResponse.body() != null) {
                                    val backendFileId = fileResponse.body()!!.id
                                    fileDao.markFileSynced(file.id, backendFileId, syncTime)
                                    Log.d("SyncWorker", "File synced: ${file.fileName}")
                                }
                            } catch (e: Exception) {
                                Log.e("SyncWorker", "Failed to sync file: ${file.fileName}", e)
                                allFilesSyncedSuccessfully = false
                                break
                            }
                        }

                        if (allFilesSyncedSuccessfully) {
                            noteDao.markNotesAsSynced(note.id, backendNoteId, syncTime)
                            successCount++
                        } else {
                            failCount++
                        }

                    } else {
                        failCount++
                        Log.e("SyncWorker", "Failed to sync note: ${noteResponse.message()}")
                    }

                } catch (e: Exception) {
                    failCount++
                    Log.e("SyncWorker", "Error syncing note: ${note.title}", e)
                }
            }

            Log.d("SyncWorker", "Sync complete. Success: $successCount, Failed: $failCount")

            return@withContext if (failCount == 0) {
                Result.success()
            } else {
                Result.retry()
            }

        } catch (e: Exception) {
            Log.d("SyncWorker", "Sync worker failed", e)
            return@withContext Result.failure()
        }
    }

    private fun readFileAsBase64(filePath: String): String {
        return try {
           val file = fileStorageRepository.getFile(filePath)
            if (file.exists()) {
                val bytes = file.readBytes()
                Base64.encodeToString(bytes, Base64.NO_WRAP)
            } else {
                ""
            }
        } catch (e: Exception) {
            Log.e("SyncWorker", "Error reading file: $filePath", e)
            ""
        }
    }
}
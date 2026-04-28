package com.example.intellecta.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.intellecta.dao.FileDao
import com.example.intellecta.dao.NoteDao
import com.example.intellecta.model.request.NoteSupabaseRequest
import com.example.intellecta.model.request.NoteUpdateRequest
import com.example.intellecta.repository.FileStorageRepository
import com.example.intellecta.viewmodel.AuthViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit


class SyncNotesWorker(
    context: Context,
    params: WorkerParameters,
    private val supabase: SupabaseClient,
    private val noteDao: NoteDao,
    private val fileDao: FileDao,
    private val fileStorageRepository: FileStorageRepository,
): CoroutineWorker(context, params) {

    companion object {
        private const val TAG = "SyncNotesWorked"
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
             * CREATE / UPDATE NOTES
             */
            val unsyncedNotes = noteDao.getUnsyncedNotes()
            Log.d(TAG, "Found ${unsyncedNotes.size} notes to sync")

            for (note in unsyncedNotes) {
                try {
                    val syncTime = System.currentTimeMillis()

                    if (note.servedId == null) {
                        // CREATE — insert new row in Supabase
                        Log.d(TAG, "Creating note: ${note.title}")

                        val response = supabase.postgrest
                            .from("notes")
                            .insert(
                                NoteSupabaseRequest(
                                    local_id = note.id,
                                    user_id = userId,
                                    title = note.title,
                                    content = note.content,
                                    summary = note.summary,
                                    category = note.category,
                                    timestamp = note.timeStamp,
                                    is_deleted = false,
                                    last_modified = syncTime
                                )
                            ) {
                                select(Columns.raw("id"))
                            }
                            .decodeSingle<Map<String, String>>()

                        val supabaseId = response["id"]!!
                        noteDao.markNotesAsSynced(note.id, supabaseId, syncTime)
                        Log.d(TAG, "Note created. Local: ${note.id} → Supabase: $supabaseId")

                    } else {
                        // UPDATE — update existing row in Supabase
                        Log.d(TAG, "Updating note: ${note.title}")

                        supabase.postgrest
                            .from("notes")
                            .update(
                                NoteUpdateRequest(
                                    title = note.title,
                                    content = note.content,
                                    summary = note.summary,
                                    category = note.category,
                                    last_modified = syncTime,
                                    is_deleted = note.isDeleted
                                )
                            ) {
                                filter { eq("id", note.servedId!!) }
                            }

                        noteDao.markNotesAsSynced(note.id, note.servedId!!, syncTime)
                        Log.d(TAG, "Note updated. Supabase ID: ${note.servedId}")
                    }
                } catch (e: Exception) {
                    failCount++
                    Log.e(TAG, "Error syncing note: ${note.title}", e)
                }
            }


            /**
             ************* DELETE NOTES  *************
             **/

            noteDao.hardDeleteUnsyncedDeletedNotes()
            Log.d(TAG, "Hard deleted never-synced notes")

            val deletedNotes = noteDao.getDeletedUnSyncedNotes()
            Log.d(TAG, "Found ${deletedNotes.size} notes to delete")


            for (note in deletedNotes) {
                try {
                    if (note.servedId == null) {
                        // never synced, just hard delete locally
                        noteDao.hardDeleteSyncedNoteById(note.id)
                        continue
                    }

                    supabase.postgrest
                        .from("notes")
                        .update(mapOf("is_deleted" to true)) {
                            filter { eq("id", note.servedId!!) }
                        }

                    noteDao.softDeleteNote(note.id)
                    Log.d(TAG, "Note deleted: ${note.title}")

                } catch (e: Exception) {
                    failCount++
                    Log.e(TAG, "Error deleting note: ${note.title}", e)
                }
            }

            Log.d(TAG, "Sync complete. Failures: $failCount")
            return@withContext if (failCount == 0) Result.success() else Result.retry()
        } catch (e: Exception) {
            Log.e(TAG, "Sync worker failed", e)
            return@withContext Result.failure()
        }

    }
}

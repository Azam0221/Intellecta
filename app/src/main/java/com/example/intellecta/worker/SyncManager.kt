package com.example.intellecta.worker

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

object SyncManager{
 
    private const val TAG = "SyncManager"
    private const val SYNC_WORK_NAME = "note_and_file_sync_chain"
    private const val PERIODIC_SYNC_WORK_NAME = "periodic_note_sync"

    fun syncNow(context: Context){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val notesSyncRequest = OneTimeWorkRequestBuilder<SyncNotesWorker>()
            .setConstraints(constraints)
            .addTag(SYNC_WORK_NAME)
            .build()

        val filesSyncRequest = OneTimeWorkRequestBuilder<SyncFileWorker>()
            .setConstraints(constraints)
            .addTag(SYNC_WORK_NAME)
            .build()

        WorkManager.getInstance(context)
            .beginUniqueWork(
                SYNC_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                notesSyncRequest
            )
            .then(filesSyncRequest)
            .enqueue()

        Log.d(TAG, "Enqueued sequential note → file sync chain")
    }

    fun setupPeriodicSync(context: Context){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicNoteSyncRequest = PeriodicWorkRequestBuilder<SyncNotesWorker>(
            15,TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .addTag(PERIODIC_SYNC_WORK_NAME)
            .build()

        val periodicFileSyncRequest = PeriodicWorkRequestBuilder<SyncFileWorker>(
            15,TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .addTag(PERIODIC_SYNC_WORK_NAME)
            .build()

        // 3. Chain them
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "periodic_note_sync",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicNoteSyncRequest
            )

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "periodic_file_sync",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicFileSyncRequest
            )
    }
}
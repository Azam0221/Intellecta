package com.example.intellecta.worker

import android.content.Context
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

    private const val SYNC_WORK_NAME = "note_sync_work"
    private const val PERIODIC_SYNC_WORK_NAME = "periodic_note_sync"

    fun syncNow(context: Context){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncNotesWorker>()
            .setConstraints(constraints)
            .addTag(SYNC_WORK_NAME)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(
                SYNC_WORK_NAME,
                ExistingWorkPolicy.REPLACE,
                syncRequest
            )
    }

    fun setupPeriodicSync(context: Context){
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicSyncRequest = PeriodicWorkRequestBuilder<SyncNotesWorker>(
            15,TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .addTag(PERIODIC_SYNC_WORK_NAME)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                PERIODIC_SYNC_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicSyncRequest
            )
    }
}
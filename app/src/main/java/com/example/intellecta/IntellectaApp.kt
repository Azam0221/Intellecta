package com.example.intellecta

import android.app.Application
import androidx.work.Configuration
import com.example.intellecta.di.appModule
import com.example.intellecta.worker.SyncManager
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.androidx.workmanager.koin.workManagerFactory

class IntellectaApp : Application(), Configuration.Provider {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@IntellectaApp)
            androidLogger()
            modules(appModule)
            workManagerFactory()
        }

        SyncManager.setupPeriodicSync(this)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(get())
            .build()


}
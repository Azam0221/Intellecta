package com.example.intellecta

import android.app.Application
import com.example.intellecta.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class IntellectaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@IntellectaApp)
            modules(appModule)
        }
    }


}
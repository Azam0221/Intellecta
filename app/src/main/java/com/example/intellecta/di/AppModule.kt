package com.example.intellecta.di

import android.app.Application
import androidx.room.Room
import com.example.intellecta.chatBot.ChatViewModel
import com.example.intellecta.dao.IntellectaDatabase
import com.example.intellecta.dao.MIGRATION_1_2
import com.example.intellecta.dao.MIGRATION_2_3
import com.example.intellecta.fileManaging.FileManager
import com.example.intellecta.repository.FileStorageRepository
import com.example.intellecta.repository.NoteRepository
import com.example.intellecta.repository.RagRepository
import com.example.intellecta.supabase_config.SupabaseConfig
import com.example.intellecta.viewmodel.AuthViewModel
import com.example.intellecta.viewmodel.FilesManagingViewModel
import com.example.intellecta.viewmodel.NoteViewModel
import com.example.intellecta.worker.SyncFileWorker
import com.example.intellecta.worker.SyncNotesWorker
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module



val appModule = module {

    // Supabase client — single instance
    single {
        SupabaseConfig.createClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_KEY
        )
    }

    // Worker
    worker {
        SyncNotesWorker(
            context = androidContext(),
            params = get(),
            supabase = get(),
            noteDao = get(),
            fileDao = get(),
            fileStorageRepository = get()
        )
    }

    worker {
        SyncFileWorker(
            context = androidContext(),
            params = get(),
            supabase = get(),
            noteDao = get(),
            fileDao = get(),
            fileStorageRepository = get()
        )
    }

    //Database singleton
    single {
        Room.databaseBuilder(
            get<Application>(),
            IntellectaDatabase::class.java,
            "intellecta.db"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }

    //DAOs
    single { get<IntellectaDatabase>().noteDao() }
    single { get<IntellectaDatabase>().fileDao() }

    //Repository
    single { NoteRepository(get(), get(), get()) }
    single { FileStorageRepository(get()) }
    single { FileManager(androidContext()) }
    single { RagRepository(get()) }


    //Viewmodel
    viewModel { NoteViewModel(get(), get(),get(),get(),androidApplication()) }
    viewModel { ChatViewModel(get()) }
    viewModel { FilesManagingViewModel(get(), get()) }
    viewModel { AuthViewModel(get()) }

}

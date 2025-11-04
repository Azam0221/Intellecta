package com.example.intellecta.di

import android.app.Application
import androidx.room.Room
import com.example.intellecta.chatBot.ChatViewModel
import com.example.intellecta.dao.IntellectaDatabase
import com.example.intellecta.data.TokenManager
import com.example.intellecta.fileManaging.FileManager
import com.example.intellecta.network.ApiService
import com.example.intellecta.network.AuthInterceptor
import com.example.intellecta.repository.FileStorageRepository
import com.example.intellecta.repository.NoteRepository
import com.example.intellecta.viewmodel.AuthViewModel
import com.example.intellecta.viewmodel.FilesManagingViewModel
import com.example.intellecta.viewmodel.NoteViewModel
import com.example.intellecta.worker.SyncNotesWorker
import com.google.ai.client.generativeai.BuildConfig
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.dsl.worker
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    // Worker

    worker {
        SyncNotesWorker(
            context = androidContext(),
            params = get(),
            apiService = get(),
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
            .fallbackToDestructiveMigration()
            .build()
    }

    //OkHttp Client with Auth Interceptor
    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(get())) // Inject TokenManager
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single { get<Retrofit>().create(ApiService::class.java) }

    //Token Manager
    single { TokenManager(get()) }

    //DAOs

    single { get<IntellectaDatabase>().noteDao() }
    single { get<IntellectaDatabase>().fileDao() }

    //Repository

    single { NoteRepository(get(), get(), get()) }
    single { FileStorageRepository(get()) }
    single { FileManager(get()) }

    //Viewmodel

    viewModel { NoteViewModel(get(), get()) }
    viewModel { ChatViewModel(get()) }
    viewModel { FilesManagingViewModel(get(), get()) }
    viewModel { AuthViewModel(get()) }

}
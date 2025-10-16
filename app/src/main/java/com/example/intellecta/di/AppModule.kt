package com.example.intellecta.di

import android.app.Application
import androidx.room.Room
import com.example.intellecta.chatBot.ChatViewModel
import com.example.intellecta.dao.IntellectaDatabase
import com.example.intellecta.fileManaging.FileManager
import com.example.intellecta.repository.FileStorageRepository
import com.example.intellecta.repository.NoteRepository
import com.example.intellecta.viewmodel.FilesManagingViewModel
import com.example.intellecta.viewmodel.NoteViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //Database singleton

    single {
        Room.databaseBuilder(
            get<Application>(),
            IntellectaDatabase::class.java,
            "intellecta.db"
        ).build()
    }

    //DAOs

    single { get<IntellectaDatabase>().noteDao() }
    single { get<IntellectaDatabase>().fileDao() }

    //Repository

    single { NoteRepository(get(),get(),get()) }
    single { FileStorageRepository(get()) }
    single { FileManager(get()) }

    //Viewmodel

    viewModel{ NoteViewModel(get(),get()) }
    viewModel{ ChatViewModel(get()) }
    viewModel{ FilesManagingViewModel(get(),get()) }

}
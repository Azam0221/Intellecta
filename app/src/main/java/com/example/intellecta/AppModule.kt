package com.example.intellecta

import android.app.Application
import androidx.room.Room
import com.example.intellecta.repository.IntellectaDatabase
import com.example.intellecta.viewmodel.NoteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    //Database singleton

    single {
        Room.databaseBuilder(
            get<Application>(),
            IntellectaDatabase::class.java,
            "intellecta_db"
        ).build()
    }

    //DAOs
    single { get<IntellectaDatabase>().noteDao() }
    single { get<IntellectaDatabase>().fileDao() }

    //Viewmodel
    //viewModel{NoteViewModel(get())}
}
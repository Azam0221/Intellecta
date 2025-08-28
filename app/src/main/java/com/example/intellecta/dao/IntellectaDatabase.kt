package com.example.intellecta.dao


import com.example.intellecta.model.Note
import com.example.intellecta.model.FileMeta
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities =  [Note::class, FileMeta::class], version = 1, exportSchema = false)
abstract class IntellectaDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun fileDao(): FileDao
} 
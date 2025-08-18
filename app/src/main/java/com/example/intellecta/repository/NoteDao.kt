package com.example.intellecta.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.intellecta.model.Note


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note) : Long

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY timeStamp")
    suspend fun getAllNotes() : List<Note>

}


package com.example.intellecta.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.intellecta.model.Note
import com.example.intellecta.model.NotesWithFiles


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note) : Long

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNote(noteId : Int) : Note

    @Query("SELECT * FROM notes ORDER BY timeStamp")
    suspend fun getAllNotes() : List<Note>

    @Transaction
    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteWithFiles(noteId : Int) : NotesWithFiles

    @Transaction
    @Query("SELECT * FROM notes")
    suspend fun getAllNotesWithFiles(): List<NotesWithFiles>


}


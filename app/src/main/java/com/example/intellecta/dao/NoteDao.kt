package com.example.intellecta.dao

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



    @Query("DELETE  FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId : Int)

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

    // SYNC query

    @Query("SELECT * FROM notes WHERE isSynced = 0 ORDER BY timeStamp ASC")
    suspend fun getUnsyncedNotes() : List<Note>


    @Query("UPDATE notes SET isSynced = 1, servedId = :servedId , lastModified = :syncTime WHERE id = :localId")
    suspend fun markNotesAsSynced(localId: Int, servedId:String, syncTime:Long ) : List<Note>

    @Query("UPDATE notes SET isSynced = 0 WHERE id = :localId")
    suspend fun markNoteUnsynced(localId: Int)

    @Query("SELECT COUNT(*) FROM notes WHERE isSynced = 0")
    suspend fun countUnsyncedNotes(): Int




}


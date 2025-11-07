package com.example.intellecta.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.intellecta.model.FileMeta
import com.example.intellecta.model.Note


@Dao
interface FileDao {

    @Insert
    suspend fun insertFile(file:FileMeta)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiles(files: List<FileMeta>)

    @Query("SELECT * FROM files WHERE noteId = :noteId")
     suspend fun getFilesForNote(noteId : Int) : List<FileMeta>

    @Query("SELECT * FROM files ORDER BY id DESC")
    suspend fun getAllFiles(): List<FileMeta>

    @Update
    suspend fun updateFile(file: FileMeta)

    @Delete
    suspend fun deleteFile(file: FileMeta)

    @Delete
    suspend fun deleteFiles(files: List<FileMeta>)

    @Query("DELETE FROM files WHERE noteId = :noteId")
    suspend fun deleteFilesForNote(noteId: Int)

    // SYNC query

    @Query("SELECT * FROM notes WHERE isSynced = 0 AND isDeleted = 0 ORDER BY timeStamp ASC")
    suspend fun getUnsyncedNotes() : List<Note>

    @Query("UPDATE notes SET isSynced = 1, servedId = :servedId , lastModified = :syncTime WHERE id = :localId")
    suspend fun markNotesAsSynced(localId: Int, servedId: String, syncTime: Long) : Int

    @Query("UPDATE notes SET isSynced = 0 WHERE id = :localId")
    suspend fun markNoteUnsynced(localId: Int)

    @Query("SELECT COUNT(*) FROM notes WHERE isSynced = 0")
    suspend fun countUnsyncedNotes(): Int

    @Query("UPDATE notes SET isDeleted = 1 , deletedAt = :deletedAt, isSynced = 0 WHERE id = :noteId")
    suspend fun softDeleteNote(noteId: Int, deletedAt: Long)

    @Query("SELECT * FROM notes WHERE isDeleted = 1 AND isSynced = 0")
    suspend fun getDeletedUnSyncedNotes() : List<Note>

    @Query("DELETE FROM notes WHERE isDeleted = 1 AND isSynced = 0")
    suspend fun hardDeleteSyncedNotes()

    @Query("DELETE FROM notes WHERE isDeleted = 1 AND isSynced = 0 AND id = :localId")
    suspend fun hardDeleteSyncedNoteById(localId: Int)

    @Query("DELETE FROM notes WHERE id = :localId")
    suspend fun hardDeleteNoteById(localId: Int)

    @Query("DELETE FROM notes WHERE isDeleted = 1 AND isSynced = 1 AND deletedAt < :cutoffTime")
    suspend fun hardDeleteOldSyncedNotes(cutoffTime: Long): Int

    @Query("UPDATE notes SET syncError = :errorMessage WHERE id = :localId")
    suspend fun markNoteSyncFailed(localId: Int, errorMessage: String)

}
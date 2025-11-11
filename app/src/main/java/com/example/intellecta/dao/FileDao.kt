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

    @Query("SELECT * FROM files WHERE isSynced = 0 AND isDeleted = 0 ORDER BY timeStamp ASC")
    suspend fun getUnsyncedFiles() : List<FileMeta>

    @Query("UPDATE files SET isSynced = 1, servedId = :servedId , lastModified = :syncTime WHERE id = :localId")
    suspend fun markFileAsSynced(localId: Int, servedId: String, syncTime: Long) : Int

    @Query("UPDATE files SET isSynced = 0 WHERE id = :localId")
    suspend fun markFileAsUnsynced(localId: Int)

    @Query("UPDATE files SET isDeleted = 1, isSynced = 0 WHERE id = :localId")
    suspend fun softDeleteFile(localId: Int)

    @Query("SELECT * FROM files WHERE isDeleted = 1 AND isSynced = 0")
    suspend fun getDeletedUnsyncedFiles(): List<FileMeta>

    @Query("DELETE FROM files WHERE id = :localId AND isDeleted = 1")
    suspend fun hardDeleteFile(localId: Int)
}
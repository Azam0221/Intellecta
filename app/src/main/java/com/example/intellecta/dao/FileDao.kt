package com.example.intellecta.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.intellecta.model.FileMeta


@Dao
interface FileDao {

    @Insert
    suspend fun insertFile(file:FileMeta)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiles(files: List<FileMeta>)

    @Query("SELECT * FROM files WHERE noteId = :noteId")
     suspend fun getFilesForNote(noteId : Int) : List<FileMeta>

    @Update
    suspend fun updateFile(file: FileMeta)

    @Delete
    suspend fun deleteFile(file: FileMeta)

    @Delete
    suspend fun deleteFiles(files: List<FileMeta>)

    @Query("DELETE FROM files WHERE noteId = :noteId")
    suspend fun deleteFilesForNote(noteId: Int)
}
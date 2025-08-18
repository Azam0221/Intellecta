package com.example.intellecta.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.intellecta.model.FileMeta


@Dao
interface FileDao {

    @Insert
    suspend fun insertFile(file:FileMeta)

    @Query("SELECT * FROM files WHERE noteId = :noteId")
     suspend fun getFilesForNote(noteId : Int) : List<FileMeta>
}
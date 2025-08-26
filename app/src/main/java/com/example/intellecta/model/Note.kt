package com.example.intellecta.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.intellecta.repository.FileDao


@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true) var id : Int =0,
    var  title: String="",
    var content: String="",
    var summary: String="",
    var category: String="",
    var timeStamp: Long = System.currentTimeMillis(),
)


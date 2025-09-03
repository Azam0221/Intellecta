package com.example.intellecta.model

import androidx.compose.ui.text.font.Font
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "files",
    foreignKeys = [ForeignKey(
        entity = Note::class,
        parentColumns = ["id"],
        childColumns = ["noteId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["noteId"])]
)
data class FileMeta(
    @PrimaryKey(autoGenerate = true)
    var id : Int =0,
    var noteId : Int, 
    var fileName : String, 
    var fileType : String,
    var fileData : String
)

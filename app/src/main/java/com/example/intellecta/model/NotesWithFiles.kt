package com.example.intellecta.model

import androidx.room.Embedded
import androidx.room.Relation

data class NotesWithFiles(
    @Embedded val note:Note,
    @Relation(
    parentColumn = "id",
    entityColumn = "noteId"
    )
    val files: List<FileMeta>
)
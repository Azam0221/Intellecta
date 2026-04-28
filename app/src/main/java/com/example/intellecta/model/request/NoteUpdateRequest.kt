package com.example.intellecta.model.request

import kotlinx.serialization.Serializable

@Serializable
data class NoteUpdateRequest(
    val title: String,
    val content: String,
    val summary: String,
    val category: String,
    val last_modified: Long,
    val is_deleted: Boolean
)
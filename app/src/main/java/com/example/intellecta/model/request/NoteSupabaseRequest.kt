package com.example.intellecta.model.request

import kotlinx.serialization.Serializable


@Serializable
data class NoteSupabaseRequest (
    val local_id: Int,
    val user_id: String,
    val title: String,
    val content: String,
    val summary: String,
    val category: String,
    val timestamp: Long,
    val is_deleted: Boolean,
    val last_modified: Long
)
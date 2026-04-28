package com.example.intellecta.model.request

import kotlinx.serialization.Serializable

@Serializable
data class FileSupabaseRequest(
    val local_id: Int,
    val note_id: String,
    val user_id: String,
    val file_name: String,
    val file_type: String,
    val supabase_url: String,
    val is_deleted: Boolean,
    val last_modified: Long
)

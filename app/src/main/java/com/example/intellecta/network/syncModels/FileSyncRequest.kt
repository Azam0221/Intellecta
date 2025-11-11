package com.example.intellecta.network.syncModels

data class FileSyncRequest(
    val servedId: String,
    val fileName: String,
    val fileType: String,
    val supabaseUrl : String
)
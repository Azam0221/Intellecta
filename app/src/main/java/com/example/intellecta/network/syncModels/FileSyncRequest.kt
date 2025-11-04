package com.example.intellecta.network.syncModels

data class FileSyncRequest(
    val noteId: String,
    val fileName: String,
    val fileType: String,
    val fileData: String
)
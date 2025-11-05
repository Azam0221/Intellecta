package com.example.intellecta.network.syncModels

data class NoteSyncRequest(
    val title: String,
    val content: String,
    val summary: String,
    val category: String,
    val timestamp: Long,
    val localId: String? = null

)
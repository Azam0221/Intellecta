package com.example.intellecta.model.response


import kotlinx.serialization.Serializable

@Serializable
data class RagResponse(
    val answer: String,
    val sources: List<String>
)

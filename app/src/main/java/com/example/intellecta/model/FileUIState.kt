package com.example.intellecta.model

data class FileUIState(
    val allFiles: List<FileMeta> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val error: String? = null
)
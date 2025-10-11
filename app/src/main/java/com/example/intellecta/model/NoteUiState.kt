package com.example.intellecta.model

data class NoteUiState(
    val note: Note = Note(),
    val notes: List<Note> = emptyList(),
    val attachedFiles: List<AttachedFile> = emptyList(),
    val fetchedFiles: List<FileMeta> = emptyList(),
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)
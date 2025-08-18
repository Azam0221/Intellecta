package com.example.intellecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intellecta.model.FileMeta
import com.example.intellecta.model.Note
import com.example.intellecta.repository.FileDao
import com.example.intellecta.repository.NoteDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteDao: NoteDao,
    private val fileDao: FileDao
 ) : ViewModel() {
    private val _uiNoteState = MutableStateFlow(Note())
    val uiNoteState: StateFlow<Note> = _uiNoteState


    // NOTE entries UPDATE

    fun onTitleChange(newValue: String) {
        _uiNoteState.update { it.copy(title = newValue) }
    }

    fun onContentChange(newValue: String) {
        _uiNoteState.update { it.copy(content = newValue) }
    }

    fun onSummaryChange(newValue: String) {
        _uiNoteState.update { it.copy(summary = newValue) }
    }

    fun onCategoryChange(newValue: String) {
        _uiNoteState.update { it.copy(category = newValue) }
    }

    fun addFile(file: FileMeta) {
        _uiNoteState.update { it.copy(attachedFiles = it.attachedFiles + file) }
    }

    fun removeFile(file: FileMeta) {
        _uiNoteState.update { it.copy(attachedFiles = it.attachedFiles - file) }
    }

    fun saveNote() {
        viewModelScope.launch {
            val note = Note(
                id = _uiNoteState.value.id,
                title = _uiNoteState.value.title,
                content = _uiNoteState.value.content,
                summary = _uiNoteState.value.summary,
                category = _uiNoteState.value.category,
                timeStamp = System.currentTimeMillis()
            )

        }
    }

    private fun buildNote(): Note{
        val state = _uiNoteState.value
        return Note(
            id = state.id,
            title = state.title,
            content = state.content,
            summary = state.summary,
            category = state.category,
            timeStamp = System.currentTimeMillis()
        )
    }
}
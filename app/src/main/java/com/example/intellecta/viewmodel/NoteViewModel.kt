package com.example.intellecta.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intellecta.model.Note
import com.example.intellecta.dao.FileDao
import com.example.intellecta.dao.NoteDao
import com.example.intellecta.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(
    private val noteRepository: NoteRepository
 ) : ViewModel() {
    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState


    // NOTE entries UPDATE

    fun updateNoteField(update: (Note) -> Note) {
        _uiState.update { it.copy(note = update(it.note)) }
    }


//    fun onTitleChange(newValue: String) {
//        _uiState.value = _uiState.value.copy(
//            note = _uiState.value.note.copy(title = newValue)
//        )
//    }
//
//    fun onContentChange(newValue: String) {
//        _uiState.value = _uiState.value.copy(
//            note = _uiState.value.note.copy(content = newValue)
//        )
//    }
//
//    fun onSummaryChange(newValue: String) {
//        _uiState.value = _uiState.value.copy(
//            note = _uiState.value.note.copy(summary = newValue)
//        )
//    }
//
//    fun onCategoryChange(newValue: String) {
//        _uiState.value = _uiState.value.copy(
//            note = _uiState.value.note.copy(category = newValue)
//        )
//    }


    fun saveNote() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val note = buildNote()
                noteRepository.insertNote(note)
                _uiState.value = _uiState.value.copy(isSaved = true, error = null , isLoading = false )
                Log.d("note" ,"notes added $note")
            }
            catch (e : Exception){
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun loadAllNotes(){
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try{
                val notes = noteRepository.getAllNotes()
                _uiState.value = _uiState.value.copy(
                    notes = notes,
                    isLoading = false,
                    error = null
                )
                Log.d("noteLoad", "all notes loaded $notes")
            }
            catch (e:Exception){
                _uiState.value = _uiState.value.copy(
                    error = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun loadNote(noteId : Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val note = noteRepository.getNote(noteId)
                _uiState.value = _uiState.value.copy(
                    note = note,
                    isLoading = false,
                    error = null
                )
                Log.d("noteLoad", "note loaded $note")
            } catch (e: Exception){
                _uiState.value = _uiState.value.copy(error = e.message)
            }

        }
    }



    fun updateNote(note: Note){
        viewModelScope.launch {
            val note = buildNote()
            noteRepository.updateNote(note)
            Log.d("note" ,"notes updated $note")
        }
    }

    private fun buildNote(): Note{
        val state = _uiState.value
        return Note(
            id = state.note.id,
            title = state.note.title,
            content = state.note.content,
            summary = state.note.summary,
            category = state.note.category,
            timeStamp = System.currentTimeMillis()
        )
    }
}

data class NoteUiState(
    val note: Note = Note(),
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)
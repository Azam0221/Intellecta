package com.example.intellecta.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intellecta.fileManaging.FileType
import com.example.intellecta.model.Note
import com.example.intellecta.repository.NoteRepository
import com.example.intellecta.model.AttachedFile
import com.example.intellecta.model.NoteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class NoteViewModel(
    private val noteRepository: NoteRepository,
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
                if (note.title.isBlank() || note.content.isBlank()) {
                    _uiState.update { it.copy(error = "Title or Content cannot be empty") }
                    return@launch
                }
                noteRepository.insertNoteWithFiles(note, _uiState.value.attachedFiles)
                _uiState.value = _uiState.value.copy(
                    isSaved = true, error = null , isLoading = false,
                    attachedFiles = emptyList()
                )
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

    fun loadNoteWithFiles(noteId : Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val noteWithFiles  = noteRepository.getNoteWithFiles(noteId)
                _uiState.value = _uiState.value.copy(
                    note = noteWithFiles.note,
                    fetchedFiles = noteWithFiles.files,
                    isLoading = false,
                    error = null
                )
                Log.d("noteLoadWithFiles", "note loaded $noteWithFiles")
            } catch (e: Exception){
                _uiState.value = _uiState.value.copy(error = e.message)
            }
        }
    }

    fun loadNoteForEdit(noteId : Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val noteWithFiles  = noteRepository.getNoteWithFiles(noteId)
                _uiState.value = _uiState.value.copy(
                    note = noteWithFiles.note,
                    attachedFiles = noteWithFiles.files.map { file->
                        AttachedFile(
                            uri = Uri.parse(file.fileData),
                            type = FileType.valueOf(file.fileType),
                            displayName = file.fileName
                        )
                    },
                    isLoading = false,
                    error = null
                )
                Log.d("noteLoadForEdit", "note loaded $noteWithFiles")
            } catch (e: Exception){
                _uiState.value = _uiState.value.copy(error = e.message)
            }

        }
    }


    fun updateNote(note: Note,newFiles:List<AttachedFile>){
        viewModelScope.launch {
            noteRepository.updateNoteWithFiles(note,newFiles)
            loadNoteWithFiles(note.id)
            Log.d("note" ,"notes updated $note")
        }
    }

    fun deleteNote(noteId: Int){
        viewModelScope.launch {
            noteRepository.deleteNote(noteId)
            loadAllNotes()
            Log.d("note" ,"notes updated $noteId")
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

    fun addFile(uri: Uri, type: FileType, displayName:String){
        _uiState.update { state ->
            state.copy(attachedFiles = state.attachedFiles + AttachedFile(uri,type,displayName))
        }
    }

    fun removeFile(uri: Uri,type: FileType){
        _uiState.update { state ->
            state.copy(attachedFiles = state.attachedFiles.filterNot { it.uri == uri })
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}


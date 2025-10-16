package com.example.intellecta.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.intellecta.fileManaging.FileManager
import com.example.intellecta.model.FileMeta
import com.example.intellecta.model.FileUIState
import com.example.intellecta.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FilesManagingViewModel(
    private val noteRepository: NoteRepository,
    private val fileManager: FileManager
): ViewModel() {
   private val _fileUiState = MutableStateFlow(FileUIState())
    val fileUIState : StateFlow<FileUIState> = _fileUiState

    fun loadFile(){
        viewModelScope.launch {
            _fileUiState.update { it.copy(isLoading = true) }
            try {
                val files = noteRepository.getAllFiles()
                _fileUiState.update {
                    it.copy(
                        allFiles = files,
                        isLoading = false,
                        isEmpty = false,
                        error = null
                    )
                }
            }catch (e:Exception){
                _fileUiState.update { it.copy(error = e.message , isLoading = false) }
            }
        }
    }

    fun openFile(fileMeta: FileMeta) {
        fileManager.openFile(fileMeta)
    }

}
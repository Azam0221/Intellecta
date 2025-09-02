package com.example.intellecta.repository

import com.example.intellecta.dao.FileDao
import com.example.intellecta.dao.NoteDao
import com.example.intellecta.model.FileMeta
import com.example.intellecta.model.Note
import com.example.intellecta.model.NotesWithFiles
import com.example.intellecta.ui.components.AttachedFile

class NoteRepository (
    private val noteDao: NoteDao,
    private val fileDao: FileDao,
    private val fileStorageRepository: FileStorageRepository
) {
    suspend fun insertNoteWithFiles(note: Note,files:List<AttachedFile>){
        val noteId = noteDao.insertNote(note)
        files.forEach { attachedFile ->
            val fileName = attachedFile.uri.lastPathSegment ?: "file_${System.currentTimeMillis()}"
            val fileMeta = FileMeta(
                noteId = noteId.toInt(),
                fileName = attachedFile.uri.lastPathSegment ?: "Unnamed",
                fileType = attachedFile.type.name,
                fileData = attachedFile.uri.toString()
            )
            fileDao.insertFile(fileMeta)
        }
    }

    suspend fun getNoteWithFiles(noteId: Int): NotesWithFiles {
        return noteDao.getNoteWithFiles(noteId)
    }

    suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    suspend fun getNote(id: Int) = noteDao.getNote(id)

    suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    suspend fun deleteNote(noteId: Int) = noteDao.deleteNoteById(noteId)

    suspend fun getAllNotes() = noteDao.getAllNotes()
} 
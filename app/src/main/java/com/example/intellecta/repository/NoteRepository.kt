package com.example.intellecta.repository

import com.example.intellecta.dao.FileDao
import com.example.intellecta.dao.NoteDao
import com.example.intellecta.model.Note

class NoteRepository (
    private val noteDao: NoteDao,
    private val fileDao: FileDao
) {
    suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    suspend fun getNote(id: Int) = noteDao.getNote(id)

    suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    suspend fun deleteNote(noteId: Int) = noteDao.deleteNoteById(noteId)

    suspend fun getAllNotes() = noteDao.getAllNotes()
} 
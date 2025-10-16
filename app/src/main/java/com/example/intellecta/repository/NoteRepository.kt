package com.example.intellecta.repository

import com.example.intellecta.dao.FileDao
import com.example.intellecta.dao.NoteDao
import com.example.intellecta.model.FileMeta
import com.example.intellecta.model.Note
import com.example.intellecta.model.NotesWithFiles
import com.example.intellecta.model.AttachedFile

class NoteRepository (
    private val noteDao: NoteDao,
    private val fileDao: FileDao,
    private val fileStorageRepository: FileStorageRepository
) {

    suspend fun insertNoteWithFiles(note: Note,files:List<AttachedFile>){
        val noteId = noteDao.insertNote(note)
        files.forEach { attachedFile ->
            val fileName = attachedFile.displayName
            val savedFile = fileStorageRepository.saveFile(attachedFile.uri,fileName)

            savedFile?.let {
                val fileMeta = FileMeta(
                    noteId = noteId.toInt(),
                    fileName = attachedFile.displayName,
                    fileType = attachedFile.type.name,
                    fileData = it.name
                )
                fileDao.insertFile(fileMeta)
            }
        }
    }

    suspend fun updateNoteWithFiles(
        note: Note,
        newFiles : List<AttachedFile>
    ){
        noteDao.updateNote(note)
        val existingFiles = noteDao.getNoteWithFiles(note.id).files
        val newEntities = newFiles.map { file ->
            FileMeta(
                noteId = note.id,
                fileName = file.uri.toString().substringAfterLast('/'),
                fileType = file.type.name,
                fileData = file.uri.toString()
            )
        }

        val toDelete = existingFiles.filterNot { old ->
            newEntities.any { it.fileData == old.fileData }
        }
        fileDao.deleteFiles(toDelete)

        val toInsert = newEntities.filterNot { new ->
            existingFiles.any { it.fileData == new.fileData }
        }
        fileDao.insertFiles(toInsert)

    }

    suspend fun getNoteWithFiles(noteId: Int): NotesWithFiles {
        return noteDao.getNoteWithFiles(noteId)
    }

    suspend fun getAllFiles(): List<FileMeta> {
        return fileDao.getAllFiles()
    }


    suspend fun insertNote(note: Note) = noteDao.insertNote(note)

    suspend fun getAllNotesWithFiles(): List<NotesWithFiles> {
        return noteDao.getAllNotesWithFiles()
    }

    suspend fun getNote(id: Int) = noteDao.getNote(id)

    suspend fun updateNote(note: Note) = noteDao.updateNote(note)

    suspend fun deleteNote(noteId: Int) = noteDao.deleteNoteById(noteId)

    suspend fun getAllNotes() = noteDao.getAllNotes()
} 
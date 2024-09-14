package com.aplussoft.jetpackcomposeroomapp.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>
    fun getNoteById(id: Int): Flow<Note?>
    fun getByTitle(title: String): Flow<Note?>
    fun getNotesOrderedByTitleAsc(): Flow<List<Note>>
    fun getNotesOrderedByTitleDesc(): Flow<List<Note>>
    suspend fun insert(note: Note)
    suspend fun update(note: Note)
    suspend fun delete(note: Note)
}

class OfflineNoteRepository @Inject constructor(private val noteDao: NoteDao) : NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> = noteDao.getAll()

    override fun getNoteById(id: Int): Flow<Note?> = noteDao.getById(id)

    override fun getByTitle(title: String): Flow<Note?> = noteDao.getByTitle(title)

    override fun getNotesOrderedByTitleAsc(): Flow<List<Note>> = noteDao.getNotesOrderedByTitleAsc()

    override fun getNotesOrderedByTitleDesc(): Flow<List<Note>> =
        noteDao.getNotesOrderedByTitleDesc()

    override suspend fun insert(note: Note) = noteDao.insert(note)

    override suspend fun update(note: Note) = noteDao.update(note)

    override suspend fun delete(note: Note) = noteDao.delete(note)

}


package com.aplussoft.jetpackcomposeroomapp.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

    @Query("SELECT * FROM Note")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM Note WHERE id = :id")
     fun getById(id: Int): Flow<Note>

    @Query("SELECT * FROM Note WHERE title = :title")
    fun getByTitle(title: String): Flow<Note>

    @Query("SELECT * FROM Note ORDER BY title ASC")
    fun getNotesOrderedByTitleAsc(): Flow<List<Note>>

    @Query("SELECT * FROM Note ORDER BY title DESC")
    fun getNotesOrderedByTitleDesc(): Flow<List<Note>>
}
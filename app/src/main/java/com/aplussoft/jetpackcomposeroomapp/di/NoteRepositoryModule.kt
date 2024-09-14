package com.aplussoft.jetpackcomposeroomapp.di

import com.aplussoft.jetpackcomposeroomapp.data.NoteDao
import com.aplussoft.jetpackcomposeroomapp.data.NoteRepository
import com.aplussoft.jetpackcomposeroomapp.data.OfflineNoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteRepositoryModule {

    @Provides
    @Singleton
    fun providesNoteRepository(noteDao: NoteDao): NoteRepository {
        return OfflineNoteRepository(noteDao = noteDao)
    }

}
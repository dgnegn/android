@file:OptIn(ExperimentalCoroutinesApi::class)

package com.aplussoft.jetpackcomposeroomapp.ui.note

import android.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aplussoft.jetpackcomposeroomapp.data.Note
import com.aplussoft.jetpackcomposeroomapp.data.NoteDao
import com.aplussoft.jetpackcomposeroomapp.ui.note.NoteEvent.HideDialog
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed interface NoteEvent {
    object SaveNote : NoteEvent
    data class SetTitle(val title: String) : NoteEvent
    data class SetBody(val body: String) : NoteEvent
    object ShowDialog : NoteEvent
    object HideDialog : NoteEvent
    data class SortNotes(val sortType: SortType) : NoteEvent
    data class DeleteNote(val note: Note) : NoteEvent


}

enum class SortType {
    TITLE_ASCENDING,
    TITLE_DESCENDING
}

data class NoteState(
    val notes: List<Note> = listOf<Note>(),
    val title: String = "",
    val body: String = "",
    val isAddingNote: Boolean = false,
    val sortType: SortType = SortType.TITLE_ASCENDING,

    )

@Suppress("UNCHECKED_CAST")
class NoteViewModel(private val noteDao: NoteDao) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.TITLE_ASCENDING)

    private val _notes = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.TITLE_ASCENDING -> noteDao.getNotesOrderedByTitleAsc()
            SortType.TITLE_DESCENDING -> noteDao.getNotesOrderedByTitleDesc()
        } as Flow<R>

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf<Note>())


    private val _state = MutableStateFlow(NoteState())

    val state = combine(_state, _sortType, _notes) { state, sortType, notes ->
        state.copy(
            notes = notes as List<Note>,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    fun onEvent(event: NoteEvent) {

        when (event) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteDao.delete(event.note)
                }
            }

            is HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingNote = false,
                        title = "",
                        body = ""
                    )
                }

            }

            is NoteEvent.SaveNote -> {
                val title = state.value.title
                val body = state.value.body

                if (title.isBlank() || body.isBlank()) {
                    return
                }

                val note = Note(title = title, body = body)

                viewModelScope.launch {
                    noteDao.insert(note)
                }
                _state.update {
                    it.copy(
                        isAddingNote = false,
                        title = "",
                        body = ""
                    )
                }
            }

            is NoteEvent.SetBody -> {

                _state.update { it.copy(body = event.body) }
            }

            is NoteEvent.SetTitle -> {

                _state.update { it.copy(title = event.title) }
            }

            is NoteEvent.ShowDialog -> {

                _state.update { it.copy(isAddingNote = true) }
            }

            is NoteEvent.SortNotes -> {
                _sortType.value = event.sortType
            }
        }
    }
}
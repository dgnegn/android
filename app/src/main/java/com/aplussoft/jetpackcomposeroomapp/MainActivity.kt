package com.aplussoft.jetpackcomposeroomapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel

import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.aplussoft.jetpackcomposeroomapp.data.AppDatabase

import com.aplussoft.jetpackcomposeroomapp.ui.note.NoteScreen
import com.aplussoft.jetpackcomposeroomapp.ui.note.NoteViewModel

import com.aplussoft.jetpackcomposeroomapp.ui.theme.JetpackComposeRoomAppTheme


class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "notes.db"
        ).build()
    }

    private val viewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(db.noteDao()) as T
                }
            }
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeRoomAppTheme {

                val state by viewModel.state.collectAsState()

                NoteScreen(state = state, onEvent = viewModel::onEvent)

            }
        }
    }
}


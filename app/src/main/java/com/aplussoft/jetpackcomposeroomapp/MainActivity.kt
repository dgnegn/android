package com.aplussoft.jetpackcomposeroomapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.aplussoft.jetpackcomposeroomapp.ui.note.NoteScreen
import com.aplussoft.jetpackcomposeroomapp.ui.note.NoteViewModel
import com.aplussoft.jetpackcomposeroomapp.ui.theme.JetpackComposeRoomAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<NoteViewModel>()

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


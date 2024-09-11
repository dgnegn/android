@file:OptIn(ExperimentalMaterial3Api::class)

package com.aplussoft.jetpackcomposeroomapp.ui.note

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,

    ) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Notes") },
                actions = {
                    IconButton(onClick = { onEvent(NoteEvent.SortNotes(SortType.TITLE_ASCENDING)) }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            contentDescription = "Sort By Title ASC"
                        )
                    }
                    IconButton(onClick = { onEvent(NoteEvent.SortNotes(SortType.TITLE_DESCENDING)) }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Sort By Title DESC"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(NoteEvent.ShowDialog) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
            }
        }
    ) { paddingValues ->
        if (state.isAddingNote) {
            AddNoteDialog(state = state, onEvent = onEvent)
        }
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(
                count = state.notes.size,
                key = { it },
                itemContent = {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        onClick = { onEvent(NoteEvent.DeleteNote(state.notes[it])) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 12.dp, bottom = 8.dp, top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column() {
                                Text(text = state.notes[it].title.toString())
                                Text(text = state.notes[it].body.toString())

                            }

                            IconButton(onClick = { onEvent(NoteEvent.DeleteNote(state.notes[it])) }) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Note"
                                )
                            }

                        }


                    }
                })
        }

    }

}

@Composable
fun AddNoteDialog(state: NoteState, onEvent: (NoteEvent) -> Unit) {

    Dialog(
        onDismissRequest = {
            onEvent(NoteEvent.HideDialog)
        }
    ) {
        Column(
            modifier = Modifier

                .background(MaterialTheme.colorScheme.background),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement
                .spacedBy(8.dp)


        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                style = TextStyle(fontSize = MaterialTheme.typography.headlineMedium.fontSize),
                text = "Add Note",
            )

            OutlinedTextField(
                value = state.title,
                onValueChange = { onEvent(NoteEvent.SetTitle(it)) },
                placeholder = { Text(text = "Title") },
                isError = state.title.isBlank(),
            )

            OutlinedTextField(
                value = state.body,
                onValueChange = { onEvent(NoteEvent.SetBody(it)) },
                placeholder = { Text(text = "Body") },
                minLines = 10,
                isError = state.body.isBlank(),
            )
            Row(

                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)


            ) {
                OutlinedButton(

                    onClick = {
                        onEvent(NoteEvent.HideDialog)
                    },
                    modifier = Modifier.weight(1f),


                    ) {
                    Text(text = "Cancel")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { onEvent(NoteEvent.SaveNote) },
                    modifier = Modifier.weight(1f),
                ) {
                    Text(text = "Save")
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

        }
    }

}
package com.aplussoft.jetpackcomposeroomapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.Instant.now

@Entity
class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val body: String?,


)
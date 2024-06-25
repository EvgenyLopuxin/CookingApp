package com.example.myapp.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Diet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val authorName: String,
    val imageAuthor: String?
)
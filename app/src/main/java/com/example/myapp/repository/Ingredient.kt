package com.example.myapp.repository

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Ingredient")
data class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    val name: String?
)
package com.example.myapp.repository

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "DietMenu",
    foreignKeys = [
        ForeignKey(
            entity = Diet::class,
            parentColumns = ["id"],
            childColumns = ["dietId"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.NO_ACTION
        )
    ],
    indices = [Index(value = ["dietId"]), Index(value = ["recipeId"])]
)
data class DietMenu(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dietId: Int,
    val recipeId: Int,
    val dayOfWeek: Int
)

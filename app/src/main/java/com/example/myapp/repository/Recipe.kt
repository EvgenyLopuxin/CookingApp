package com.example.myapp.repository

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Recipe",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
    )]
)
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val instructions: String?,
    val imageRecipe: String? = null,
    val categoryId: Int,
    var isFav: Int?,
    val protein: Double?,
    val fat: Double?,
    val carbohydrates: Double?,
    val calories: Double?,
)

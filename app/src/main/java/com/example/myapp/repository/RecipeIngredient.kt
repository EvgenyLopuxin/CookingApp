package com.example.myapp.repository

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "RecipeIngredient",
    primaryKeys = ["recipeId", "ingredientId"],
    foreignKeys = [
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["id"],
            childColumns = ["recipeId"],
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Ingredient::class,
            parentColumns = ["id"],
            childColumns = ["ingredientId"]
        )
    ]
)
data class RecipeIngredient(
    val recipeId: Int,
    val ingredientId: Int,
    val quantity: Double,
    val unit: String
)

package com.example.myapp.repository

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface recipeDao {

    @Query("SELECT * FROM Recipe WHERE title LIKE :query")
    fun searchRecipes(query: String): Flow<List<Recipe>>

    @Query("SELECT * FROM Recipe WHERE id IN (SELECT recipeId FROM RecipeIngredient WHERE ingredientId IN (SELECT id FROM Ingredient WHERE name LIKE :ingredientQuery))")
    fun searchRecipesByIngredient(ingredientQuery: String): Flow<List<Recipe>>

    @Query("SELECT * FROM Category")
    fun getAllCategories(): Flow<List<Category>>

    @Query("SELECT * FROM DietMenu WHERE dietId = :dietId AND dayOfWeek = :dayOfWeek ORDER BY id ASC")
    suspend fun getRecipesForDay(dietId: Int, dayOfWeek: Int): List<DietMenu>

    @Query("SELECT * FROM Diet")
    suspend fun getAllDiets(): List<Diet>

    @Query("SELECT * FROM recipe WHERE isFav = 1")
    fun getFavoriteRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM Recipe")
    fun getAllRecipes(): Flow<List<Recipe>>

    @Query("SELECT name FROM Diet WHERE id = :dietId")
    fun getDietNameById(dietId: Int): String

    @Query("SELECT * FROM Recipe WHERE id = :recipeId LIMIT 1")
    suspend fun getRecipeById(recipeId: Int): Recipe

    @Query("UPDATE Recipe SET isFav = :isFavorite WHERE id = :recipeId")
    suspend fun updateRecipeFavoriteStatus(recipeId: Int, isFavorite: Boolean)

    @Query("SELECT * FROM Recipe WHERE categoryId = :categoryId")
    fun getRecipesByCategoryId(categoryId: Int): Flow<List<Recipe>>

    @Query("""
        SELECT Ingredient.name, RecipeIngredient.quantity, RecipeIngredient.unit
        FROM Ingredient
        INNER JOIN RecipeIngredient ON Ingredient.id = RecipeIngredient.ingredientId
        WHERE RecipeIngredient.recipeId = :recipeId
    """)
    suspend fun getIngredientsByRecipeId(recipeId: Int): List<IngredientWithQuantity>
}


data class IngredientWithQuantity(
    val name: String,
    val quantity: Double,
    val unit: String
)
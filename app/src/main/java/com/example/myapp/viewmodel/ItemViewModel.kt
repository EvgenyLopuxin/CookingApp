package com.example.myapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.repository.AppDatabase
import com.example.myapp.repository.IngredientWithQuantity
import com.example.myapp.repository.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ItemViewModel : ViewModel() {
    private val _recipe = MutableLiveData<Recipe>()
    val recipe: LiveData<Recipe> get() = _recipe

    private val _ingredients = MutableLiveData<List<IngredientWithQuantity>>()
    val ingredients: LiveData<List<IngredientWithQuantity>> get() = _ingredients

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    fun fetchRecipeDetails(recipeId: Int, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dao = AppDatabase.getDb(context).getDao()
                val recipe = dao.getRecipeById(recipeId)
                val ingredients = dao.getIngredientsByRecipeId(recipeId)
                _recipe.postValue(recipe)
                _ingredients.postValue(ingredients)
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue("Error loading recipe details")
            }
        }
    }

    fun toggleFavoriteStatus(context: Context) {
        val currentRecipe = _recipe.value ?: return
        val newFavStatus = if (currentRecipe.isFav == 1) 0 else 1
        currentRecipe.isFav = newFavStatus

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val dao = AppDatabase.getDb(context).getDao()
                dao.updateRecipeFavoriteStatus(currentRecipe.id, currentRecipe.isFav == 1)
                _recipe.postValue(currentRecipe)
            } catch (e: Exception) {
                e.printStackTrace()
                _errorMessage.postValue("Error updating favorite status")
            }
        }
    }

    fun clearErrorMessage() {
        _errorMessage.value = null
    }
}

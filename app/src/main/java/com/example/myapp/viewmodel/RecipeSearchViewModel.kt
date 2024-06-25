package com.example.myapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.repository.AppDatabase
import com.example.myapp.repository.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RecipeSearchViewModel : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes

    fun searchRecipes(query: String, context: Context) {
        val database = AppDatabase.getDb(context)
        val dao = database.getDao()

        viewModelScope.launch(Dispatchers.IO) {
            if (query.isEmpty()) {
                _recipes.postValue(emptyList())
            } else {
                val recipesByNameFlow = dao.searchRecipes("%$query%")
                val recipesByIngredientFlow = dao.searchRecipesByIngredient("%$query%")

                combine(recipesByNameFlow, recipesByIngredientFlow) { recipesByName, recipesByIngredient ->
                    (recipesByName + recipesByIngredient).distinct()
                }.flowOn(Dispatchers.IO).collect { combinedList ->
                    _recipes.postValue(combinedList)
                }
            }
        }
    }
}

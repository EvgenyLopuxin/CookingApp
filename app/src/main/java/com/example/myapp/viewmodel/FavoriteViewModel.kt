package com.example.myapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapp.repository.AppDatabase
import com.example.myapp.repository.Recipe
import com.example.myapp.repository.recipeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    private val dao: recipeDao = AppDatabase.getDb(application).getDao()

    val favoriteRecipes = MutableLiveData<List<Recipe>>()

    fun fetchFavoriteRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.getFavoriteRecipes().collect { recipes ->
                favoriteRecipes.postValue(recipes)
            }
        }
    }
}

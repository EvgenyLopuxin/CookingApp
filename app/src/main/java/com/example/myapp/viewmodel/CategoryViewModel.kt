package com.example.myapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.repository.AppDatabase
import com.example.myapp.repository.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> get() = _recipes

    fun fetchRecipesByCategoryId(categoryId: Int, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val dao = AppDatabase.getDb(context).getDao()
            dao.getRecipesByCategoryId(categoryId).collect { recipes ->
                _recipes.postValue(recipes)
            }
        }
    }
}

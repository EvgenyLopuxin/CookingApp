package com.example.myapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapp.repository.AppDatabase
import com.example.myapp.repository.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DietMenuViewModel(application: Application) : AndroidViewModel(application) {

    private val _mondayRecipes = MutableLiveData<List<Recipe>>()
    val mondayRecipes: LiveData<List<Recipe>> get() = _mondayRecipes

    private val _tuesdayRecipes = MutableLiveData<List<Recipe>>()
    val tuesdayRecipes: LiveData<List<Recipe>> get() = _tuesdayRecipes

    private val _wednesdayRecipes = MutableLiveData<List<Recipe>>()
    val wednesdayRecipes: LiveData<List<Recipe>> get() = _wednesdayRecipes

    private val _thursdayRecipes = MutableLiveData<List<Recipe>>()
    val thursdayRecipes: LiveData<List<Recipe>> get() = _thursdayRecipes

    private val _fridayRecipes = MutableLiveData<List<Recipe>>()
    val fridayRecipes: LiveData<List<Recipe>> get() = _fridayRecipes

    private val _saturdayRecipes = MutableLiveData<List<Recipe>>()
    val saturdayRecipes: LiveData<List<Recipe>> get() = _saturdayRecipes

    private val _sundayRecipes = MutableLiveData<List<Recipe>>()
    val sundayRecipes: LiveData<List<Recipe>> get() = _sundayRecipes

    private val _dietName = MutableLiveData<String>()
    val dietName: LiveData<String> get() = _dietName

    fun fetchDietMenus(dietId: Int) {
        val database = AppDatabase.getDb(getApplication())
        val recipeDao = database.getDao()

        viewModelScope.launch(Dispatchers.IO) {
            _dietName.postValue(recipeDao.getDietNameById(dietId))
            _mondayRecipes.postValue(recipeDao.getRecipesForDay(dietId, 1).map { recipeDao.getRecipeById(it.recipeId) })
            _tuesdayRecipes.postValue(recipeDao.getRecipesForDay(dietId, 2).map { recipeDao.getRecipeById(it.recipeId) })
            _wednesdayRecipes.postValue(recipeDao.getRecipesForDay(dietId, 3).map { recipeDao.getRecipeById(it.recipeId) })
            _thursdayRecipes.postValue(recipeDao.getRecipesForDay(dietId, 4).map { recipeDao.getRecipeById(it.recipeId) })
            _fridayRecipes.postValue(recipeDao.getRecipesForDay(dietId, 5).map { recipeDao.getRecipeById(it.recipeId) })
            _saturdayRecipes.postValue(recipeDao.getRecipesForDay(dietId, 6).map { recipeDao.getRecipeById(it.recipeId) })
            _sundayRecipes.postValue(recipeDao.getRecipesForDay(dietId, 7).map { recipeDao.getRecipeById(it.recipeId) })
        }
    }
}

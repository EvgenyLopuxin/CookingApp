package com.example.myapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapp.repository.AppDatabase
import com.example.myapp.repository.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class homeViewModel : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> get() = _categories

    fun fetchCategories(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val dao = AppDatabase.getDb(context).getDao()
            dao.getAllCategories().collect { categories ->
                _categories.postValue(categories)
            }
        }
    }
}

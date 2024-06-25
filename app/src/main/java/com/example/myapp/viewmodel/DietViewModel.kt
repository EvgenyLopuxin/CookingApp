package com.example.myapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapp.repository.AppDatabase
import com.example.myapp.repository.Diet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DietViewModel(application: Application) : AndroidViewModel(application) {

    private val _diets = MutableLiveData<List<Diet>>()
    val diets: LiveData<List<Diet>> get() = _diets

    init {
        fetchDiets()
    }

    private fun fetchDiets() {
        val database = AppDatabase.getDb(getApplication())
        val dietDao = database.getDao()

        viewModelScope.launch(Dispatchers.IO) {
            val fetchedDiets = dietDao.getAllDiets()
            _diets.postValue(fetchedDiets)
        }
    }
}

package com.bogdan.choresmap.Frontend.ViewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bogdan.choresmap.Backend.ChoreRepository

class ChoreViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChoreViewModel::class.java)) {
            val repository = ChoreRepository(application)
            return ChoreViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
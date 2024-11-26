package com.bogdan.choresmap.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChoreViewModel : ViewModel() {
    // Shared state for chores
    private val _chores = MutableStateFlow<List<Chore>>(emptyList())
    val chores: StateFlow<List<Chore>> get() = _chores

    // Function to add chore
    fun addChore(chore: Chore) {
        _chores.value = _chores.value + chore
    }

    // Function to remove chore
    fun removeChore(chore: Chore) {
        _chores.value = _chores.value - chore
    }
}
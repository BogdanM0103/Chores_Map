package com.bogdan.choresmap.model

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ChoreViewModel(application: Application) : AndroidViewModel(application) {
    private val _chores = MutableLiveData<List<Chore>>(emptyList())
    val chores: LiveData<List<Chore>> = _chores

    fun addChore(chore: Chore, locationViewModel: LocationViewModel, context: Context) {
        val currentChores = _chores.value.orEmpty().toMutableList()
        currentChores.add(chore)
        _chores.value = currentChores

        // Add a geofence around the new chore
        chore.location?.let {
            locationViewModel.addGeofence(context, chore.id.toString(), it)
        }
    }

    // Function to remove chore
    fun removeChore(chore: Chore) {
        _chores.value = _chores.value?.minus(chore)
    }
}
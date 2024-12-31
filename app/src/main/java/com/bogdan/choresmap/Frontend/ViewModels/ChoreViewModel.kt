package com.bogdan.choresmap.Frontend.ViewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bogdan.choresmap.Backend.Chore
import com.bogdan.choresmap.Backend.ChoreRepository
import kotlinx.coroutines.launch

class ChoreViewModel(application: Application, private val repository: ChoreRepository) : AndroidViewModel(application) {
    val chores: LiveData<List<Chore>> = repository.getAllChores()

    fun addChore(chore: Chore, locationViewModel: LocationViewModel, context: Context) {
        viewModelScope.launch {
            repository.insertChore(chore)

            // Add a geofence if the chore has a valid location
            chore.location?.let { location ->
                locationViewModel.addGeofence(
                    context = context,
                    id = chore.id.toString(),
                    latLng = location
                )
            }
        }
    }

    fun removeChore(chore: Chore) {
        viewModelScope.launch {
            repository.deleteChore(chore)
        }
    }

    fun updateCompletionStatus(id: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            repository.updateCompletionStatus(id, isCompleted)
        }
    }
//    private val _chores = MutableLiveData<List<Chore>>(emptyList())
//    val chores: LiveData<List<Chore>> = _chores
//
//    fun addChore(chore: Chore, locationViewModel: LocationViewModel, context: Context) {
//        val currentChores = _chores.value.orEmpty().toMutableList()
//        currentChores.add(chore)
//        _chores.value = currentChores
//
//        // Add a geofence around the new chore
//        chore.location?.let {
//            locationViewModel.addGeofence(context, chore.id.toString(), it)
//        }
//    }
//
//    // Function to remove chore
//    fun removeChore(chore: Chore) {
//        _chores.value = _chores.value?.minus(chore)
//    }
}
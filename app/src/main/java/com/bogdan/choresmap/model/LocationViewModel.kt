package com.bogdan.choresmap.model

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * To make the location accessible across screens, store it in your LocationViewModel.
 */

class LocationViewModel : ViewModel() {
    private val _userLocation = MutableStateFlow<LatLng?>(null)
    val userLocation : StateFlow<LatLng?> get() = _userLocation

    fun setUserLocation(location: LatLng?) {
        _userLocation.value = location
    }
}
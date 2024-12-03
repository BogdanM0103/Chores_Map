package com.bogdan.choresmap.model

import android.Manifest
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    private val _userLocation = MutableLiveData<LatLng?>()
    var userLocation: LiveData<LatLng?> = _userLocation

    // Initialize the GeofencingClient
    private val geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(application.applicationContext)

    fun fetchLocation(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Use a HandlerThread to ensure a separate thread for location updates
                val handlerThread = HandlerThread("LocationThread").apply { start() }
                val looper = handlerThread.looper

                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    1000L, // Minimum time interval between location updates (ms)
                    1f,    // Minimum distance between location updates (meters)
                    { location ->
                        // Post value to LiveData on the main thread
                        CoroutineScope(Dispatchers.Main).launch {
                            _userLocation.value = LatLng(location.latitude, location.longitude)
                        }
                    },
                    looper // Use the dedicated thread's looper
                )

                // Optional: Add a stop mechanism if needed
                // Example: Call locationManager.removeUpdates() to stop updates when the task is done
            } else {
                // Handle permission denial gracefully
                CoroutineScope(Dispatchers.Main).launch {
                    Log.e("FetchLocation", "Location permission not granted.")
                }
            }
        }
    }

    fun addGeofence(context: Context, geofenceId: String, latLng: LatLng, radius: Float) {
        val geofence = Geofence.Builder()
            .setRequestId(geofenceId)
            .setCircularRegion(latLng.latitude, latLng.longitude, radius)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
            .build()

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(context, GeofenceBroadcastReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                geofencingClient.addGeofences(geofencingRequest, pendingIntent).await()
                Log.d("LocationViewModel", "Geofence added: $geofenceId")
            } catch (e: Exception) {
                Log.e("LocationViewModel", "Failed to add geofence: ${e.message}")
            }
        }
    }
}
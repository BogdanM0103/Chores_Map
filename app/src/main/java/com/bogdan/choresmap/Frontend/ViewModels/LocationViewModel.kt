package com.bogdan.choresmap.Frontend.ViewModels

import android.Manifest
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bogdan.choresmap.Backend.Receivers.GeofenceBroadcastReceiver
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocationViewModel(application: Application) : AndroidViewModel(application) {

//    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
    val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private val geofencingClient = LocationServices.getGeofencingClient(application)
    private val _userLocation = MutableLiveData<LatLng>()
    val userLocation: LiveData<LatLng> = _userLocation

    init {
//        startLocationUpdates()
        ensureGPSProvider(application)
        requestGPSUpdates(application)
    }

    private val locationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        1000
    ).setMinUpdateDistanceMeters(0f).build()

    private var lastLocation: Location? = null

    fun ensureGPSProvider(context: Context) {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("LocationManager", "GPS is disabled. Prompting user to enable.")
            // You can prompt the user to enable GPS here
        }
    }

    private fun requestGPSUpdates(context: Context) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0L, // Minimum time interval between updates in milliseconds
                1f // Minimum distance between updates in meters
            ) { location ->
                Log.d("LocationManager", "GPS Location: ${location.latitude}, ${location.longitude}")
                if (lastLocation == null || location.distanceTo(lastLocation!!) > 10) {
                    lastLocation = location
                    viewModelScope.launch(Dispatchers.Default) {
                        val latLng = LatLng(location.latitude, location.longitude)
                        withContext(Dispatchers.Main) {
                            _userLocation.postValue(latLng)
                        }
                    }
                }
            }
        } else {
            Log.d("LocationManager", "Fine location permission not granted.")
        }
    }

//    private val locationCallback = object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            super.onLocationResult(locationResult)
//            for (location in locationResult.locations) {
//                if (location.provider == LocationManager.GPS_PROVIDER) {
//                    Log.d("LocationCallback", "Using GPS location: ${location.latitude}, ${location.longitude}")
//                    if (lastLocation == null || location.distanceTo(lastLocation!!) > 1) {
//                        lastLocation = location
//                        viewModelScope.launch(Dispatchers.Default) {
//                            val latLng = LatLng(location.latitude, location.longitude)
//                            withContext(Dispatchers.Main) {
//                                _userLocation.postValue(latLng)
//                            }
//                        }
//                    }
//                } else {
//                    Log.d("LocationCallback", "Non-GPS location ignored")
//                }
//            }
//        }
//    }

//    fun startLocationUpdates() {
//        if (ActivityCompat.checkSelfPermission(
//                getApplication(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
//        }
//    }

    fun addGeofence(context: Context, id: String, latLng: LatLng, radius: Float = 300f) {
        val geofence = Geofence.Builder()
            .setRequestId(id)
            .setCircularRegion(latLng.latitude, latLng.longitude, radius)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .build()

        val geofencingRequest = GeofencingRequest.Builder()
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .addGeofence(geofence)
            .build()

        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        val pendingIntent: PendingIntent by lazy {
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }

        val fineLocationGranted = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val backgroundLocationGranted = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

        Log.d("Geofencing", "Fine Location Granted: $fineLocationGranted")
        Log.d("Geofencing", "Background Location Granted: $backgroundLocationGranted")

        if (fineLocationGranted && backgroundLocationGranted) {
            geofencingClient.addGeofences(geofencingRequest, pendingIntent).run {
                addOnSuccessListener {
                    Log.d("Geofencing", "Geofence added successfully!")
                }
                addOnFailureListener {
                    Log.d("Geofencing", "Geofence was not added! ${it.message}")
                }
            }
        } else {
            Log.d("Geofencing", "Required location permissions not granted")
        }
    }
}
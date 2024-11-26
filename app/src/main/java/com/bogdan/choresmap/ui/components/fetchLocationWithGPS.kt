package com.bogdan.choresmap.ui.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat

fun fetchLocationWithGPS(context: Context, onLocationReceived: (Location) -> Unit) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            1000L, // Minimum time interval between updates (in milliseconds)
            1f     // Minimum distance between updates (in meters)
        ) { location ->
            onLocationReceived(location)
        }
    }
}
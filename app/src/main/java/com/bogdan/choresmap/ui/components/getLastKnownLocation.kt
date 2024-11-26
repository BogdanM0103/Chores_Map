package com.bogdan.choresmap.ui.components

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.model.LatLng

fun getLastKnownLocation(context: android.content.Context): LatLng? {
    val locationManager = context.getSystemService(android.content.Context.LOCATION_SERVICE) as android.location.LocationManager
    val provider = android.location.LocationManager.GPS_PROVIDER

    if (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        val location = locationManager.getLastKnownLocation(provider)
        return location?.let { LatLng(it.latitude, it.longitude) }
    }
    return null
}

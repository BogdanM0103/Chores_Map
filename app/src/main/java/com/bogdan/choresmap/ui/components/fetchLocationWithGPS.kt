package com.bogdan.choresmap.ui.components

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat

/*
    The `fetchLocationWithGPS` function continuously retrieves the user's current location using the device's GPS.

    Parameters:
    - `context`: Provides access to system services, such as `LocationManager`.
    - `onLocationReceived`: A callback function that processes the retrieved `Location` object.

    Functionality:
    1. Obtains the `LocationManager` system service from the context to manage location updates.
    2. Checks if the `ACCESS_FINE_LOCATION` permission is granted:
       - If the permission is missing, the function does nothing and exits.
    3. Requests periodic location updates using `GPS_PROVIDER`:
       - Specifies a minimum time interval (`1000L`, 1 second) between location updates.
       - Specifies a minimum distance interval (`1f`, 1 meter) for location changes.
       - Calls the `onLocationReceived` callback with the `Location` object whenever an update is received.

    This function is suitable for scenarios where the user's location needs to be tracked continuously with GPS precision.
*/

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
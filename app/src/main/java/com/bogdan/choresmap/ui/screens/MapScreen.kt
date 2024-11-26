package com.bogdan.choresmap.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var userLocation by remember { mutableStateOf<LatLng?>(null) }

    // Request location permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            fetchLocationWithGPS(context) { location ->
                userLocation = LatLng(location.latitude, location.longitude)
            }
        }
    }

    // Check for permissions and fetch location
    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fetchLocationWithGPS(context) { location ->
                userLocation = LatLng(location.latitude, location.longitude)
            }
        } else {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    val cameraPositionState = rememberCameraPositionState()

    // Update camera position when location changes
    LaunchedEffect(userLocation) {
        userLocation?.let {
            cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 15f)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            uiSettings = com.google.maps.android.compose.MapUiSettings(
                myLocationButtonEnabled = true
            ),
            properties = com.google.maps.android.compose.MapProperties(
                isMyLocationEnabled = ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            )
        )
        // Home Icon Button overlaid at the bottom center
        IconButton(
            onClick = { navController.navigate("home") },
            modifier = Modifier
                .align(Alignment.BottomCenter) // Align to the bottom center
                .padding(16.dp) // Add some padding from the screen edges
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Go to Home",
                modifier = Modifier.size(48.dp) // Set size for the icon
            )
        }
    }
}

private fun fetchLocationWithGPS(context: Context, onLocationReceived: (Location) -> Unit) {
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

private fun getLastKnownLocation(context: android.content.Context): LatLng? {
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

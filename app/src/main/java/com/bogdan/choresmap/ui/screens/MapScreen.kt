package com.bogdan.choresmap.ui.screens

import android.Manifest
import android.content.pm.PackageManager
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
import com.bogdan.choresmap.model.ChoreViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.bogdan.choresmap.ui.components.fetchLocationWithGPS
import com.google.maps.android.compose.Marker

@Composable
fun MapScreen(
    navController: NavHostController,
    choreViewModel: ChoreViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    val chores by choreViewModel.chores.collectAsState()

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
                myLocationButtonEnabled = true,
                zoomControlsEnabled = true
            ),
            properties = com.google.maps.android.compose.MapProperties(
                isMyLocationEnabled = ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ),
            chores.forEach { chore ->
                Marker(
                    position = chore.location,
                    title = chore.name,
                    snippet = chore.description
                )
            }
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
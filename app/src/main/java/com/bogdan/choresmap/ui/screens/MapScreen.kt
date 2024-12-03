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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import com.bogdan.choresmap.model.ChoreViewModel
import com.bogdan.choresmap.model.LocationViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MapScreen(
    navController: NavHostController,
    choreViewModel: ChoreViewModel,
    locationViewModel: LocationViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val userLocation by locationViewModel.userLocation.observeAsState()
    val chores by choreViewModel.chores.collectAsState()

    val cameraPositionState = rememberCameraPositionState()

    val choreMarkers by remember { mutableStateOf(mutableListOf<MarkerState>()) }

    // Update camera position when user location changes
    LaunchedEffect(userLocation) {
        userLocation?.let {
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(it, 15f)
            cameraPositionState.animate(cameraUpdate, 1000)
        }
    }

    // Prepare markers in a background thread
    LaunchedEffect(chores) {
        withContext(Dispatchers.Default) {
            val markers = chores.mapNotNull { chore ->
                chore.location?.let { location ->
                    MarkerState(position = location)
                }
            }
            withContext(Dispatchers.Main) {
                choreMarkers.clear()
                choreMarkers.addAll(markers)
            }
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
            )
        ) {
            // Add markers for each chore
            choreMarkers.forEach { markerState ->
                Marker(
                    state = markerState,
                    title = "Chore",
                    snippet = "Chore description" // Adjust with actual chore details if needed
                )
            }
        }
        // Home Icon Button overlaid at the bottom center
        IconButton(
            onClick = { navController.navigate("home") },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Go to Home",
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
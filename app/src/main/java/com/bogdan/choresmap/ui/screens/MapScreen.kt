package com.bogdan.choresmap.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    // Set Timișoara as the default location
    val timisoaraLocation = LatLng(45.75372, 21.22571)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(timisoaraLocation, 12f) // Zoom level: 12
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 32.dp)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        )
    }
}

@Preview
@Composable
fun MapScreenPreview() {
    MapScreen(navController = rememberNavController())
}

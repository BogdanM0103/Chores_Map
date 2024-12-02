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
import com.google.maps.android.compose.MarkerState

@Composable
fun MapScreen(
    navController: NavHostController,
    choreViewModel: ChoreViewModel,
    modifier: Modifier = Modifier
) {
    // context provides access to the current application or activity environment.
    // It is used to interact with Android-specific components, such as fetching
    // location suggestions using the Places API.
    val context = LocalContext.current

    // userLocation represents the current geographic location of the user.
    // It is used to track the user's position, typically retrieved from the device's GPS sensor.
    var userLocation by remember { mutableStateOf<LatLng?>(null) }


    // This line collects the current list of chores as a state from the choreViewModel.
    // `chores` is a State object that automatically updates when the list of chores in the
    // `choreViewModel` changes. This ensures the UI stays in sync with the data stored
    // in the ViewModel. By using `collectAsState()`, we observe changes in the Flow of chores
    // provided by the ViewModel and update the `chores` variable accordingly.
    val chores by choreViewModel.chores.collectAsState()

    /*
        This function initializes a launcher for requesting runtime permissions and handles the user's response.

        - `val permissionLauncher`: Creates a permission launcher using Jetpack Compose's `rememberLauncherForActivityResult`.
        - `ActivityResultContracts.RequestPermission()`: A predefined contract that simplifies requesting a single runtime permission.
        - `isGranted`: A Boolean parameter in the callback indicating whether the user granted the permission.
          - If `isGranted` is true:
            - Calls `fetchLocationWithGPS(context)`: A function that retrieves the user's current GPS location.
            - The retrieved location is used to update the `userLocation` variable with the latitude and longitude, wrapped in a `LatLng` object.
          - If `isGranted` is false:
            - No action is performed, as the user denied the permission.

        This launcher is designed to be triggered when a specific permission (e.g., location) is required, ensuring proper handling of runtime permissions in Android.
    */
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            fetchLocationWithGPS(context) { location ->
                userLocation = LatLng(location.latitude, location.longitude)
            }
        }
    }

    /*
        This block of code uses `LaunchedEffect` in Jetpack Compose to execute a side effect that checks for location permissions and either fetches the user's location or requests the necessary permission.

        - `LaunchedEffect(Unit)`:
          - A composable function that triggers its code block whenever the given `key` changes.
          - In this case, `Unit` is used as the key, ensuring the effect runs only once when the composable enters the composition.

        - **Permission Check**:
          - `ActivityCompat.checkSelfPermission` checks if the app has been granted the `ACCESS_FINE_LOCATION` permission.
          - The `context` is used to perform this check in the current Android application.

        - **If Permission is Granted**:
          - The `fetchLocationWithGPS` function is called to retrieve the user's current location using the GPS provider.
          - The retrieved latitude and longitude are wrapped into a `LatLng` object and stored in the `userLocation` variable for further use.

        - **If Permission is Not Granted**:
          - The `permissionLauncher` is triggered to request the `ACCESS_FINE_LOCATION` permission from the user dynamically.

        ### Purpose:
        This code ensures that the app has the required location permission and either fetches the user's location or requests the permission as needed. It is designed to handle permissions dynamically, providing a seamless user experience and compliance with Android's runtime permission model.
    */
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

    /**
     * Create and remember a CameraPositionState instance to manage the camera's position on the Google Map.
     * This state object allows you to programmatically control and update the camera's position and zoom level.
     */
    val cameraPositionState = rememberCameraPositionState()

    // A LaunchedEffect block is used to perform a side effect whenever the `userLocation` value changes.
    // This ensures that the camera position on the map updates whenever the user's location is updated.
    LaunchedEffect(userLocation) {
        // Check if `userLocation` is not null before proceeding.
        userLocation?.let {
            // Update the camera's position to focus on the user's current location.
            // The `CameraPosition.fromLatLngZoom` method sets the camera to the specified latitude and longitude
            // (given by `it` which represents the user's location) and zooms in to a level of 15f.
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
            )
        ) {
            // Add markers for each chore
            chores.forEach { chore ->
                chore.location?.let {
                    MarkerState(position = it)
                }?.let {
                    Marker(
                        state = it,
                        title = chore.name,
                        snippet = chore.description
                    )
                }
            }
        }
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
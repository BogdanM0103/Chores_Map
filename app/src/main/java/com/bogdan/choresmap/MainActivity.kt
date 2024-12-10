package com.bogdan.choresmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.bogdan.choresmap.model.ChoreViewModel
import com.bogdan.choresmap.model.LocationViewModel
import com.bogdan.choresmap.ui.components.AppNavigation
import com.bogdan.choresmap.ui.theme.ChoresMapTheme
import com.google.android.libraries.places.api.Places
import com.bogdan.choresmap.model.RequestPermissions

class MainActivity : ComponentActivity() {

    // Initializing LocationViewModel for managing user location
    private val locationViewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

//        // Initialize the Places API
//        if (!Places.isInitialized()) {
//            Places.initialize(applicationContext, "AIzaSyDok9QgrTQGCJX0EyLuC3IKrhLuNQpvnKc")
//        }

        setContent {
            ChoresMapApp(locationViewModel)
        }
    }
}

@Composable
fun ChoresMapApp(locationViewModel: LocationViewModel) {
    val context = LocalContext.current
//
//    // Permission launcher for requesting FINE_LOCATION at runtime
//    val permissionLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestPermission()
//    ) { isGranted ->
//        if (isGranted) {
//            locationViewModel.startLocationUpdates()
//        } else {
//            Toast.makeText(context, "Location permission denied.", Toast.LENGTH_SHORT).show()
//        }
//    }

    RequestPermissions()

    // Initialize Places API in a LaunchedEffect to perform side effects in a proper Composable context
    LaunchedEffect(Unit) {
        if (!Places.isInitialized()) {
            Places.initialize(context.applicationContext, "AIzaSyDok9QgrTQGCJX0EyLuC3IKrhLuNQpvnKc")
        }
    }

//    // Check if permission is granted; if not, request it
//    LaunchedEffect(Unit) {
//        if (ActivityCompat.checkSelfPermission(
//                context,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
//        } else {
//            locationViewModel.startLocationUpdates()
//        }
//    }

    ChoresMapTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            val navController = rememberNavController()
            val choreViewModel: ChoreViewModel = viewModel()

            AppNavigation(
                navController = navController,
                choreViewModel = choreViewModel,
                locationViewModel = locationViewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

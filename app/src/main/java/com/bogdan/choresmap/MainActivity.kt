package com.bogdan.choresmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.bogdan.choresmap.model.ChoreViewModel
import com.bogdan.choresmap.ui.components.AppNavigation
import com.bogdan.choresmap.ui.theme.ChoresMapTheme
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize the Places API
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyDok9QgrTQGCJX0EyLuC3IKrhLuNQpvnKc")
        }

        setContent {
            ChoresMapTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    /*
                    This is the navigation controller.
                    The central coordinator for managing navigation between destinations.
                    The controller offers methods for navigating between destinations, handling deep links,
                    managing the back stack, and more.
                    * */
                    val navController = rememberNavController()


                    val choreViewModel : ChoreViewModel = viewModel()

                    AppNavigation(
                        navController = navController,
                        choreViewModel = choreViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
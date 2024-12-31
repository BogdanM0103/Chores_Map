package com.bogdan.choresmap.Frontend.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bogdan.choresmap.Frontend.ViewModels.ChoreViewModel
import com.bogdan.choresmap.Frontend.ViewModels.LocationViewModel
import com.bogdan.choresmap.Frontend.Screens.AddChoreScreen.AddChoreScreen
import com.bogdan.choresmap.Frontend.Screens.HomeScreen.HomeScreen
import com.bogdan.choresmap.Frontend.Screens.MapScreen.MapScreen

import androidx.compose.runtime.*
import kotlinx.coroutines.delay

/*
* This function handles everything Navigation related and is called in the MainActivity.
* */
@Composable
fun AppNavigation(
    navController: NavHostController,
    choreViewModel: ChoreViewModel,
    locationViewModel: LocationViewModel,
    modifier: Modifier
) {
    // State to manage whether navigation is currently allowed
    var isNavigating by remember { mutableStateOf(false) }

    NavHost(navController = navController, startDestination = "home") {

        composable("home") {
            HomeScreen(
                navController = navController,
                choreViewModel = choreViewModel,
                onAddChoreClick = {
                    if (!isNavigating) {
                        isNavigating = true
                        navController.navigate("addChore")
                    }
                },
                onMapClick = {
                    if (!isNavigating) {
                        isNavigating = true
                        navController.navigate("map")
                    }
                }
            )

            // Reset the `isNavigating` flag after a small delay to prevent rapid navigation
            LaunchedEffect(isNavigating) {
                if (isNavigating) {
                    delay(300) // Adjust the delay duration as per your needs
                    isNavigating = false
                }
            }
        }

        composable("addChore") {
            AddChoreScreen(
                navController = navController,
                choreViewModel = choreViewModel,
                locationViewModel = locationViewModel
            )
        }

        composable("map") {
            MapScreen(
                navController = navController,
                choreViewModel = choreViewModel,
                locationViewModel = locationViewModel,
                modifier = Modifier
            )
        }
    }
}
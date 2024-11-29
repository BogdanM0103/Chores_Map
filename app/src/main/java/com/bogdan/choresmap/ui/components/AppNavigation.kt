package com.bogdan.choresmap.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bogdan.choresmap.model.ChoreViewModel
import com.bogdan.choresmap.ui.screens.AddChoreScreen
import com.bogdan.choresmap.ui.screens.HomeScreen
import com.bogdan.choresmap.ui.screens.MapScreen

// This handles the navigation between screens.
@Composable
fun AppNavigation(
    navController: NavHostController,
    choreViewModel: ChoreViewModel,
    modifier: Modifier
) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, choreViewModel = choreViewModel)
        }
        composable("addChore") {
            AddChoreScreen(navController, choreViewModel = choreViewModel)
        }
        composable("map") {
            MapScreen(navController, choreViewModel = choreViewModel, modifier = Modifier)
        }
    }
}
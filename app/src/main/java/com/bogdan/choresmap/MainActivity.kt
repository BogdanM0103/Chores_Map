package com.bogdan.choresmap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bogdan.choresmap.model.ChoreViewModel
import com.bogdan.choresmap.ui.screens.AddChoreScreen
import com.bogdan.choresmap.ui.theme.ChoresMapTheme
import com.bogdan.choresmap.ui.screens.HomeScreen
import com.bogdan.choresmap.ui.screens.MapScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChoresMapTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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
            MapScreen(navController)
        }
    }
}
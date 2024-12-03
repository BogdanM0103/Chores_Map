package com.bogdan.choresmap.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bogdan.choresmap.model.ChoreViewModel
import com.bogdan.choresmap.model.LocationViewModel
import com.bogdan.choresmap.ui.screens.AddChoreScreen
import com.bogdan.choresmap.ui.screens.HomeScreen
import com.bogdan.choresmap.ui.screens.MapScreen

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
    NavHost(navController = navController, startDestination = "home") {

        /*
        * The lambda passed to the NavHost ultimately calls NavController.createGraph() and returns a NavGraph.
        * Each route is supplied as a type argument to NavGraphBuilder.composable<T>() which adds the destination to the resulting NavGraph.
        * */

        /*
        * To better understand the lambda that creates the NavGraph, consider that to build the same graph as in the preceding snippet,
        * you could create the NavGraph separately using
        * NavController.createGraph() and pass it to the NavHost directly:
        val navGraph by remember(navController) {
            navController.createGraph(startDestination = Profile)) {
                composable<Profile> { ProfileScreen( /* ... */ ) }
                composable<FriendsList> { FriendsListScreen( /* ... */ ) }
            }
        }
        NavHost(navController, navGraph)
         */

        composable("home") {
            /*
            * The lambda passed to composable is what the NavHost displays for that destination.
            * */
            HomeScreen(navController, choreViewModel = choreViewModel)
        }
        composable("addChore") {
            AddChoreScreen(navController, choreViewModel = choreViewModel)
        }
        composable("map") {
            MapScreen(navController, choreViewModel = choreViewModel, locationViewModel = locationViewModel, modifier = Modifier)
        }
    }
}
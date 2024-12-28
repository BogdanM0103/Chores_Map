package com.bogdan.choresmap.Frontend.Screens.HomeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.bogdan.choresmap.Frontend.ViewModels.ChoreViewModel

/*
    The main screen of the application from which all the other screens can be accessed
 */
@Composable
fun HomeScreen(
    navController: NavHostController,
    choreViewModel: ChoreViewModel,
    onAddChoreClick: () -> Unit,
    onMapClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val chores = choreViewModel.chores.observeAsState().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 60.dp,
//                bottom = 100.dp
            ) // Optional padding for screen edges
    ) {
        // Calling the List of Chores
        if (chores != null) {
            ChoreList(
                chores = chores,
                onDeleteChore = { chore -> choreViewModel.removeChore(chore) },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 120.dp) // Reserve space for buttons
            )
        }

        // This Box contains the AddChoreButton and the MapButton pinned at the bottom of the screen
        Box(
            modifier = Modifier
                // aligns the Box with buttons at the bottom of the screen
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // AddChoreButton is called
                AddChoreButton(
                    onClick = {
//                        navController.navigate("addChore")
                        onAddChoreClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 16.dp)
                )

                // Map Button is called
                MapButton(
                    onClick = {
//                        navController.navigate("map")
                        onMapClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp) // Add spacing between buttons
                )
            }
        }
    }
}

//@Preview
//@Composable
//fun HomeScreenPreview() {
//    HomeScreen(navController = rememberNavController(), choreViewModel = ChoreViewModel())
//}
package com.bogdan.choresmap.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bogdan.choresmap.model.ChoreRepository
import com.bogdan.choresmap.ui.components.AddChoreButton
import com.bogdan.choresmap.ui.components.ChoreList

@Composable
fun HomeScreen(modifier: Modifier) {
    var chores by remember {
        mutableStateOf(ChoreRepository.getChore())
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, bottom = 32.dp) // Optional padding for screen edges
    ) {
        // List of chores at the top, takes up remaining space
        ChoreList(
            chores = chores,
            modifier = Modifier
                .padding(bottom = 80.dp) // Add padding to avoid overlap with button
        )

        // Button aligned to the bottom
        AddChoreButton(
            onClick = {},
            modifier = Modifier
                .align(Alignment.BottomCenter)
//                .fillMaxWidth()
                .height(50.dp)
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(modifier = Modifier)
}

//@Composable
//fun ChoresList(modifier: Modifier) {
//    var chores by remember {
//        mutableStateOf(ChoreRepository.getChore())
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 60.dp, bottom = 32.dp) // Optional padding for screen edges
//    ) {
//        // List of chores at the top, takes up remaining space
//        ChoreList(
//            chores = chores,
//            modifier = Modifier
//                .padding(bottom = 80.dp) // Add padding to avoid overlap with button
//        )
//
//        // Button aligned to the bottom
//        AddChoreButton(
//            onClick = {},
//            modifier = Modifier
//                .align(Alignment.BottomCenter)
////                .fillMaxWidth()
//                .height(50.dp)
//        )
//    }
//}
//
//@Preview
//@Composable
//fun ChoresListTestPreview() {
//    ChoresMapTheme {
//        ChoresList(modifier = Modifier)
//    }
//}
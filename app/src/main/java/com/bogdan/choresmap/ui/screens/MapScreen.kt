package com.bogdan.choresmap.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun MapScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    
}

@Preview
@Composable
fun MapScreenPreview() {
    MapScreen(navController = rememberNavController())
}
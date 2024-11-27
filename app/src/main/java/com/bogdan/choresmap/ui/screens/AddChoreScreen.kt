package com.bogdan.choresmap.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bogdan.choresmap.model.Chore
import com.bogdan.choresmap.model.ChoreViewModel
import com.bogdan.choresmap.ui.components.ConfirmButton
import com.bogdan.choresmap.ui.components.fetchPlacesAutocomplete
import kotlinx.coroutines.launch

// The form of the chore that will be added
@Composable
fun AddChoreScreen(
    navController: NavHostController,
    choreViewModel: ChoreViewModel,
    modifier: Modifier = Modifier
) {
    var choreName by remember { mutableStateOf("") } // Now works correctly
    var choreDescription by remember { mutableStateOf("") }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var placeQuery by remember { mutableStateOf("") }
    var autocompleteSuggestions by remember { mutableStateOf(listOf<String>()) }
    var selectedPlace by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Input Field
        TextField(
            value = choreName,
            onValueChange = { choreName = it },
            label = { Text("Chore Name") },
            modifier = Modifier
                .fillMaxWidth()
        )

        // Places API Autocomplete input
        TextField(
            value = placeQuery,
            onValueChange = { query ->
                placeQuery = query
                coroutineScope.launch {
                    fetchPlacesAutocomplete(context, query) { suggestions ->
                        autocompleteSuggestions = suggestions
                    }
                }
            },
            label = { Text("Location") },
            modifier = Modifier.fillMaxWidth()
        )

        // Display autocomplete suggestions
        autocompleteSuggestions.forEach { suggestion ->
            ClickableText(
                text = AnnotatedString(suggestion),
                onClick = {
                    selectedPlace = suggestion
                    placeQuery = suggestion
                    autocompleteSuggestions = emptyList() // Clear suggestions
                },
                modifier = Modifier.padding(8.dp)
            )
        }

        // Description Field
        TextField(
            value = choreDescription,
            onValueChange = { choreDescription = it },
            label = {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Description",
                        style = TextStyle(
                            fontSize = if (choreDescription.length > 30) 12.sp else 16.sp
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        // Confirm Button
        ConfirmButton(
            onClick = {
                if (choreName.isNotBlank() && choreDescription.isNotBlank()) {
                    choreViewModel.addChore(
                        Chore(
                            id = System.currentTimeMillis().toInt(),
                            name = choreName
                        )
                    )
                    navController.navigate("home")
                }
            },
            modifier = Modifier
//                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun AddChoreScreenPreview() {
    AddChoreScreen(
        navController = rememberNavController(),
        choreViewModel = ChoreViewModel(),
        modifier = Modifier
    )
}

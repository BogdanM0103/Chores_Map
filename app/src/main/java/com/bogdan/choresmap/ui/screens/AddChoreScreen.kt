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
import com.bogdan.choresmap.ui.components.geocodePlace
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

@Composable
fun AddChoreScreen(
    navController: NavHostController,
    choreViewModel: ChoreViewModel,
    modifier: Modifier = Modifier
) {

    /*
        Any changes to value schedules recomposition of any composable functions that read value.
        The value is retained after navigating to a different Screen.
     */

    // Stores the name of the Chore
    var choreName by remember { mutableStateOf("") }

    // Store the description of the Chore
    var choreDescription by remember { mutableStateOf("") }

    // Context is used to access resources and system services, including starting geofencing operations or
    // requesting permissions
    val context = LocalContext.current

    // CoroutineScope is used in AddChoreScreen to launch asynchronous tasks such as fetching autocomplete suggestions
    // for the location input field, ensuring these operations do not block the main thread and maintain a responsive UI.
    val coroutineScope = rememberCoroutineScope()

    // placeQuery holds the current text entered in the location input field.
    // It is used to trigger the Places API autocomplete suggestions based on user input.
    var placeQuery by remember { mutableStateOf("") }

    // autocompleteSuggestions holds the list of location suggestions fetched from the Places API.
    // These suggestions are displayed below the location input field for the user to select.
    var autocompleteSuggestions by remember { mutableStateOf(listOf<String>()) }

    // selectedPlace stores the location selected by the user from the autocomplete suggestions.
    // It is used to associate a specific place with the chore being created.
    var selectedPlace by remember { mutableStateOf<LatLng?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Input Field for Chore Name
        TextField(
            value = choreName,
            onValueChange = { choreName = it },
            label = { Text("Chore Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Places API Autocomplete Input
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

        // Autocomplete Suggestions
        autocompleteSuggestions.forEach { suggestion ->
            ClickableText(
                text = AnnotatedString(suggestion),
                onClick = {
                    val geocodedLatLng = geocodePlace(context, suggestion)
                    selectedPlace = geocodedLatLng
                    placeQuery = suggestion
                    autocompleteSuggestions = emptyList() // Clear suggestions
                },
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Description Field
        TextField(
            value = choreDescription,
            onValueChange = { choreDescription = it },
            label = {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Description",
                        style = TextStyle(
                            //fontSize = if (choreDescription.length > 30) 12.sp else 16.sp
                        )
                    )
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Small space between the buttons
        Spacer(modifier = Modifier.height(16.dp))

        /*
            ConfirmButton is called and it takes the user back to HomeScreen
         */
        ConfirmButton(
            onClick = {
                // Verifies if the name of the chore is not empty.
                // Verifies if the description of the chore is not empty.
                // Verifies if the user chose a location.
                if (choreName.isNotBlank() && choreDescription.isNotBlank() && selectedPlace != null) {
                    choreViewModel.addChore(
                        Chore(
                            id = System.currentTimeMillis().toInt(),
                            name = choreName,
                            description = choreDescription,
                            location = selectedPlace!! // Store as string or geocode as LatLng, ensure location is not null
                        )
                    )
                    // Navigating to HomeScreen
                    navController.navigate("home")
                }
            },
            modifier = Modifier.fillMaxWidth()
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

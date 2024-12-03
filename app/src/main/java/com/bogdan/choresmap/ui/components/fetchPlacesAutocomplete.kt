package com.bogdan.choresmap.ui.components

import android.content.Context
import android.util.Log
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

fun fetchPlacesAutocomplete(
    context: Context,
    query: String,
    onSuggestionsFetched: (List<String>) -> Unit
) {
    if (query.isEmpty()) {
        onSuggestionsFetched(emptyList())
        return
    }

    CoroutineScope(Dispatchers.IO).launch {
        val placesClient: PlacesClient = Places.createClient(context)
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()

        try {
            val response = placesClient.findAutocompletePredictions(request).await()
            val suggestions = response.autocompletePredictions.map { it.getFullText(null).toString() }
            withContext(Dispatchers.Main) {
                onSuggestionsFetched(suggestions)
            }
        } catch (e: Exception) {
            Log.e("PlacesAPI", "Error fetching autocomplete suggestions: ${e.message}")
            withContext(Dispatchers.Main) {
                onSuggestionsFetched(emptyList())
            }
        }
    }
}

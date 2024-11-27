package com.bogdan.choresmap.ui.components

import android.content.Context
import android.util.Log
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient

fun fetchPlacesAutocomplete(
    context: Context,
    query: String,
    onSuggestionsFetched: (List<String>) -> Unit
) {
    if (query.isEmpty()) {
        onSuggestionsFetched(emptyList())
        return
    }

    val placesClient: PlacesClient = Places.createClient(context)
    val request = FindAutocompletePredictionsRequest.builder()
        .setQuery(query)
        .build()

    placesClient.findAutocompletePredictions(request)
        .addOnSuccessListener { response ->
            val suggestions = response.autocompletePredictions.map { it.getFullText(null).toString() }
            onSuggestionsFetched(suggestions)
        }
        .addOnFailureListener { exception ->
            Log.e("PlacesAPI", "Error fetching autocomplete suggestions: ${exception.message}")
            onSuggestionsFetched(emptyList())
        }
}
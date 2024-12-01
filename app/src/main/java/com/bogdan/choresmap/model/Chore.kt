package com.bogdan.choresmap.model

import com.google.android.gms.maps.model.LatLng

/*
    This class defines a Chore
 */

data class Chore(
    val id: Int,
    val name: String,
    val description: String,
    val location: LatLng?,
    var isCompleted: Boolean = false
)
package com.bogdan.choresmap.ui.components

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.io.IOException

fun geocodePlace(context: Context, placeName: String): LatLng? {
    val geocoder = Geocoder(context)
    return try {
        val addressList = geocoder.getFromLocationName(placeName, 1)
        if (addressList.isNullOrEmpty()) null else LatLng(addressList[0].latitude, addressList[0].longitude)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}
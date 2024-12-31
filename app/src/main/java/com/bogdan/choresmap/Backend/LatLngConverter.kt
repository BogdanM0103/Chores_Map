package com.bogdan.choresmap.Backend

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng

class LatLngConverter {

    @TypeConverter
    fun fromLatLng(latLng: LatLng?): String? {
        return latLng?.let { "${it.latitude},${it.longitude}" }
    }

    @TypeConverter
    fun toLatLng(data: String?): LatLng? {
        return data?.split(",")?.let {
            LatLng(it[0].toDouble(), it[1].toDouble())
        }
    }
}
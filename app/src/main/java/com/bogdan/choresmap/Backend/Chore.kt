package com.bogdan.choresmap.Backend

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

/*
    This class defines a Chore
 */

@Entity(tableName = "chores")
data class Chore(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val location: LatLng?,
    val isCompleted: Boolean = false
)
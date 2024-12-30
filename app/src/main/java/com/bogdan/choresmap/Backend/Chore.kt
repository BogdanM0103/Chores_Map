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
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "location") val location: LatLng?,
    @ColumnInfo(name = "isCompleted") val isCompleted: Boolean = false
)
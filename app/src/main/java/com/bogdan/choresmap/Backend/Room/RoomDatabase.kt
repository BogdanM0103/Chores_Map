package com.bogdan.choresmap.Backend.Room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bogdan.choresmap.Backend.Chore
import com.bogdan.choresmap.Backend.ChoreDao

@Database(entities = [Chore::class], version = 1)
abstract class ChoreRoomDatabase : RoomDatabase() {
    abstract fun choreDao() : ChoreDao
}
package com.bogdan.choresmap.Backend

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Chore::class], version = 1)
@TypeConverters(LatLngConverter::class)
abstract class ChoreRoomDatabase : RoomDatabase() {
    abstract fun choreDao() : ChoreDao

    companion object {
        @Volatile
        private var INSTANCE: ChoreRoomDatabase? = null

        fun getDatabase(context: Context): ChoreRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChoreRoomDatabase::class.java,
                    "chore_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
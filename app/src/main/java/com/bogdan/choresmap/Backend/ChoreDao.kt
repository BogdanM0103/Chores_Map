package com.bogdan.choresmap.Backend

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChoreDao {
    @Query("SELECT * FROM chores")
    suspend fun getAllChores(): List<Chore>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChore(chore: Chore)

    @Query("DELETE FROM chores")
    suspend fun deleteAllChores()
}
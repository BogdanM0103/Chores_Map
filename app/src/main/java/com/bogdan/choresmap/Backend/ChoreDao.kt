package com.bogdan.choresmap.Backend

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChoreDao {
    @Query("SELECT * FROM chores")
    fun getAll(): List<Chore>

    @Insert
    fun insertChore(chore: Chore)

    @Delete
    fun delete(user: Chore)
}
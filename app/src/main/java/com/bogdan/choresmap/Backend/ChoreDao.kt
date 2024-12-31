package com.bogdan.choresmap.Backend

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ChoreDao {

    // Insert a single chore into the database
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(chore: Chore)

    // Insert multiple chores at once
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(chores: List<Chore>)

    // Delete a specific chore from the database
    @Delete
    suspend fun delete(chore: Chore)

    // Delete all chores
    @Query("DELETE FROM chores")
    suspend fun deleteAll()

    // Retrieve all chores from the database
    @Query("SELECT * FROM chores")
    fun getAllChores(): LiveData<List<Chore>>

    // Retrieve a specific chore by its ID
    @Query("SELECT * FROM chores WHERE id = :id")
    fun getChoreById(id: Int): LiveData<Chore>

    // Update a chore
    @Update
    suspend fun update(chore: Chore)

    // Mark a chore as completed
    @Query("UPDATE chores SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateCompletionStatus(id: Int, isCompleted: Boolean)
}
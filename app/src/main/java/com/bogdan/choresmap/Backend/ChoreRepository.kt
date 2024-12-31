package com.bogdan.choresmap.Backend

import android.content.Context
import androidx.lifecycle.LiveData
import com.google.firebase.database.FirebaseDatabase

class ChoreRepository(context: Context) {
    private val choreDao: ChoreDao
    private val firebaseDatabase = FirebaseDatabase.getInstance().getReference("chores")


    init {
        val database = ChoreRoomDatabase.getDatabase(context)
        choreDao = database.choreDao()
    }

    fun getAllChores(): LiveData<List<Chore>> {
        return choreDao.getAllChores()
    }

    suspend fun insertChore(chore: Chore) {
        choreDao.insert(chore)

        // Write to Firebase
        firebaseDatabase.child(chore.id.toString()).setValue(chore)
    }

    suspend fun deleteChore(chore: Chore) {
        choreDao.delete(chore)

        // Remove from Firebase
        firebaseDatabase.child(chore.id.toString()).removeValue()
    }

    suspend fun updateChore(chore: Chore) {
        choreDao.update(chore)
    }

    suspend fun updateCompletionStatus(id: Int, isCompleted: Boolean) {
        choreDao.updateCompletionStatus(id, isCompleted)
    }
}
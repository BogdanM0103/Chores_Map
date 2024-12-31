package com.bogdan.choresmap.Backend

import android.content.Context
import androidx.lifecycle.LiveData

class ChoreRepository(context: Context) {
    private val choreDao: ChoreDao

    init {
        val database = ChoreRoomDatabase.getDatabase(context)
        choreDao = database.choreDao()
    }

    fun getAllChores(): LiveData<List<Chore>> {
        return choreDao.getAllChores()
    }

    suspend fun insertChore(chore: Chore) {
        choreDao.insert(chore)
    }

    suspend fun deleteChore(chore: Chore) {
        choreDao.delete(chore)
    }

    suspend fun updateChore(chore: Chore) {
        choreDao.update(chore)
    }

    suspend fun updateCompletionStatus(id: Int, isCompleted: Boolean) {
        choreDao.updateCompletionStatus(id, isCompleted)
    }
}
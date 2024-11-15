package com.bogdan.choresmap.model

object ChoreRepository {
    fun getChore() : List<Chore> {
        return listOf(
            Chore(id = 1, name = "Chore 1"),
            Chore(id = 2, name = "Chore 2"),
            Chore(id = 3, name = "Chore 3")
        )
    }
}
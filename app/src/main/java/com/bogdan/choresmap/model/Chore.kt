package com.bogdan.choresmap.model

data class Chore(
    private val id: Int,
    private val name: String,
    private val isCompleted: Boolean = false
) {
    val getId: Int
        get() = id

    val getName: String
        get() = name

    val getIsCompleted: Boolean
        get() = isCompleted
}
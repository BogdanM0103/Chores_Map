package com.bogdan.choresmap.model

data class Chore(
    val id: Int,
    val name: String,
    var isCompleted: Boolean = false
)
package com.globalcorp.taskman.models


data class Mission(
    val id: Int,
    val title: String,
    val location: String,
    val description: String,
    val date: String,
    val timeStart: String,
    val timeStop: String,
    val userId: Int
)

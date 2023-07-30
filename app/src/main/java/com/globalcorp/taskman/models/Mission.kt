package com.globalcorp.taskman.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mission(
    val id: String,
    val title: String,
    val type: String,
    val location: String,
    val description: String,
    val date: String,
    val timeStart: String,
    val timeStop: String,
    val userId: String?,
    val status: Int
) : Parcelable

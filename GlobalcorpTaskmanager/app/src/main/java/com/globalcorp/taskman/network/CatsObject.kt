package com.globalcorp.taskman.network

import android.icu.text.ListFormatter.Width
import com.squareup.moshi.Json

data class CatsObject(
    val id: String,
    @Json(name = "url") val imgUrl: String,
    val width: Int,
    val height: Int

)
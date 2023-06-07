package com.globalcorp.taskman.models

import android.icu.text.ListFormatter.Width
import com.squareup.moshi.Json

data class CatObject(
    val id: String,
    @Json(name = "url") val imgUrl: String,
    val width: Int,
    val height: Int

)
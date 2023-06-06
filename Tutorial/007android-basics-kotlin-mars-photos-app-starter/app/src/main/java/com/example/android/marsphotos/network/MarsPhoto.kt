package com.example.android.marsphotos.network

import com.squareup.moshi.Json

data class MarsPhoto(
    val id: String,
    /*To use variable names in your data class that differ from the
     key names in the JSON response, use the @Json annotation.
      In this example, the name of the variable in the data class is imgSrcUrl.
      The variable can be mapped to the JSON attribute img_src
      using @Json(name = "img_src").
     */
    @Json(name = "img_src") val imgSrcUrl: String


)
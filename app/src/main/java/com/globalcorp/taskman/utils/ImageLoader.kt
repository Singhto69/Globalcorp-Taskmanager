package com.globalcorp.taskman.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import coil.ImageLoader
import coil.memory.MemoryCache
import coil.request.ImageRequest


class ImageLoader(context: Context) {
    lateinit var catImageLoader: coil.ImageLoader

    var myContext: Context = context

    init {
        catImageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .build()

    }

    fun loadImage(url: String, imageView: ImageView) {
        val request = ImageRequest.Builder(myContext)
            .data(url)
            .crossfade(true)
            .target(imageView)
            .build()
        catImageLoader.enqueue(request)
    }
}




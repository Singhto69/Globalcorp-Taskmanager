package com.globalcorp.taskman.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import coil.ImageLoader
import coil.memory.MemoryCache
import coil.request.ImageRequest


class ImageLoader(context: Context) {
    var imageLoader: ImageLoader? = null

    init {
        imageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .build()
    }

    fun loadImage(context: Context, url: String, imageView: ImageView) {
        val request = ImageRequest.Builder(context)
            .data(url)
            .crossfade(true)
            .target(imageView)
            .build()

        imageLoader?.enqueue(request)
    }
}



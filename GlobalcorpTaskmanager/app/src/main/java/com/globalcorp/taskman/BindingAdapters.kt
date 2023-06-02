package com.globalcorp.taskman

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.globalcorp.taskman.R
import com.globalcorp.taskman.models.Mission

/* @BindingAdapter annotation tells data binding to execute this binding
 adapter when a View item has the imageUrl attribute.
 */

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Mission>?) {
    val adapter = recyclerView.adapter as MissionAdapter
    adapter.submitList(data)
}

/**
 * Uses the Coil library to load an image by URL into an [ImageView]
 */
/*@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}*/
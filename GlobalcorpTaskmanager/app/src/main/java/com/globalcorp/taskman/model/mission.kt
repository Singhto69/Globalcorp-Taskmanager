package com.globalcorp.taskman.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes


data class mission(
    //@StringRes val stringResourceId: Int
    var id: Int,
    var title: String,
    var location: String,
    var description: String,
    var Date: String,
    var timeStart: String,
    var timeStop: String,
)

package com.example.a003_affirmations.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class affirmation(
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
) {

}


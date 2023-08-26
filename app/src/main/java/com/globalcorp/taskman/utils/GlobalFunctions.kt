package com.globalcorp.taskman.utils

import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.viewModelScope
import com.globalcorp.taskman.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random


fun meow(context: Context, coroutineScope: CoroutineScope) {
    coroutineScope.launch {
        val rand: Int = Random.nextInt(0, 9)
        var catPath: Int = 0
        when (rand) {
            0 -> catPath = R.raw.cat_meow_0
            1 -> catPath = R.raw.cat_meow_1
            2 -> catPath = R.raw.cat_meow_2
            3 -> catPath = R.raw.cat_meow_3
            4 -> catPath = R.raw.cat_meow_4
            5 -> catPath = R.raw.cat_meow_5
            6 -> catPath = R.raw.cat_meow_6
            7 -> catPath = R.raw.cat_meow_7
            8 -> catPath = R.raw.cat_meow_8
            9 -> catPath = R.raw.cat_meow_9
        }
        var mediaPlayer: MediaPlayer? = MediaPlayer.create(context, catPath)
        mediaPlayer?.start()
        delay(5000)
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
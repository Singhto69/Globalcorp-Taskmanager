package com.globalcorp.taskman

import android.content.Context
import android.media.MediaPlayer
import android.provider.Contacts
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.globalcorp.taskman.network.CatApiService.CatApi
import com.globalcorp.taskman.models.CatObject
import kotlinx.coroutines.delay
import kotlin.random.Random


class KittensViewModel() : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _images = MutableLiveData<List<CatObject>>()
    val images: LiveData<List<CatObject>> = _images

    val isRefreshing = MutableLiveData<Boolean>(false)

    init {
        apiGetCats()
    }

    fun refresh() {
        isRefreshing.value = true
        apiGetCats()
        isRefreshing.value = false
    }


    fun meow(context: Context) {
        viewModelScope.launch {
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

    private fun apiGetCats() {
        viewModelScope.launch {
            try {
                val listResult = CatApi.retrofitService.getCat()
                _status.value = "Success: ${listResult} retrieved"
                _images.value = listResult


            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

}


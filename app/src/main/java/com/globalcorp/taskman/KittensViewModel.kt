package com.globalcorp.taskman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.globalcorp.taskman.network.CatApiService.CatApi
import com.globalcorp.taskman.network.CatObject
import com.globalcorp.taskman.utils.ImageLoader


class KittensViewModel() : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _images = MutableLiveData<List<CatObject>>()
    val images: LiveData<List<CatObject>> = _images


    init {
        getCat()
    }


    private fun getCat() {
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
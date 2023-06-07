package com.globalcorp.taskman

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.globalcorp.taskman.network.CatApiService.CatApi
import com.globalcorp.taskman.models.CatObject


class KittensViewModel() : ViewModel() {
    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private val _images = MutableLiveData<List<CatObject>>()
    val images: LiveData<List<CatObject>> = _images

    init {
        apiGetCats()
    }

    fun refresh(){
        apiGetCats()
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


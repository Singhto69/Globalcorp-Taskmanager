package com.globalcorp.taskman

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.globalcorp.taskman.network.CatApiService.CatApi



class KittensViewModel : ViewModel() {
    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status


    init {
        getCat()
    }


    private fun getCat() {
        viewModelScope.launch {
            try {
                val listResult = CatApi.retrofitService.getCat()
                _status.value = "Success: ${listResult} retrieved"

            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }

    private fun getCats() {
        viewModelScope.launch {
            try {
                val listResult = CatApi.retrofitService.getCat()
                _status.value = "Success: ${listResult} retrieved"

            } catch (e: Exception) {
                _status.value = "Failure: ${e.message}"
            }
        }
    }
}
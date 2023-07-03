package com.globalcorp.taskman

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.globalcorp.taskman.database.MissionsDao
import com.globalcorp.taskman.ui.login.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import java.util.HashMap


class CreateNewMissionViewModel() : ViewModel() {
    
}

class CreateNewMissionViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateNewMissionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return CreateNewMissionViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
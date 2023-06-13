package com.globalcorp.taskman.ui.login

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.globalcorp.taskman.MissionsViewModel
import com.globalcorp.taskman.R
import com.globalcorp.taskman.database.MissionsDao
import com.google.errorprone.annotations.Var

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.AuthResult

class LoginViewModel(private val auth: FirebaseAuth) : ViewModel() {

    var success: State.Success = State.Success("Hi")


}

class LoginViewModelFactory(private val auth: FirebaseAuth) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") return LoginViewModel(auth) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
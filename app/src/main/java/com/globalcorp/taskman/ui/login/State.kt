package com.globalcorp.taskman.ui.login

sealed class State {
    object Loading : State()
    data class Success(val data: Any) : State()
    data class Error(val message: String) : State()
}
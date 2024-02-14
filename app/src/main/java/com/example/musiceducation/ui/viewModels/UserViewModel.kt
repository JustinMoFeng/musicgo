package com.example.musiceducation.ui.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musiceducation.MusicEducationApplication
import com.example.musiceducation.data.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(
    private val authenticateRepository: UserRepository
) : ViewModel(){
    var register_name by mutableStateOf("")
    var register_password by mutableStateOf("")
    var register_nickname by mutableStateOf("")

    var login_name by mutableStateOf("")
    var login_password by mutableStateOf("")

    var registerState by mutableStateOf("")
    var loginState by mutableStateOf("")

    fun register(){
        viewModelScope.launch {
            val string = authenticateRepository.register(register_name, register_password, register_nickname)
            Log.d("UserViewModel", "register: $string")
            registerState = string
        }
    }

    fun login(){
        loginState = "login"
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MusicEducationApplication)
                val userRepository = application.container.userRepository
                UserViewModel(userRepository)
            }
        }
    }

}
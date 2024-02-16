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
import com.example.musiceducation.entity.User
import com.example.musiceducation.utils.SharedPreferencesManager
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf

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

    var user_name by mutableStateOf("")
    var user_nickname by mutableStateOf("尚未登录")
    var user_avatar by mutableStateOf("")
    var user_request_info by mutableStateOf("")

    fun register(){
        viewModelScope.launch {
            val string = authenticateRepository.register(register_name, register_password, register_nickname)
            Log.d("UserViewModel", "register: $string")
            registerState = string
        }
    }

    fun login(){
        viewModelScope.launch {
            val string = authenticateRepository.login(login_name, login_password)
            Log.d("UserViewModel", "login: $string")
            loginState = string
        }
    }

    fun logout(){
        SharedPreferencesManager.deleteToken()
        user_name = ""
        user_nickname = "尚未登录"
        user_avatar = ""
    }

    fun getUserInfo(){
        viewModelScope.launch {
            val user = authenticateRepository.getUserInfo()
            Log.d("UserViewModel", "getUserInfo: $user")
            // 判断user的类型是不是User
            // user 可能是User，也可能是String
            if (user is User){
                user_name = user.username
                user_nickname = user.nickname
                user_avatar = user.avatar_url
            }else if(user is String){
                user_request_info = user
            }
        }
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
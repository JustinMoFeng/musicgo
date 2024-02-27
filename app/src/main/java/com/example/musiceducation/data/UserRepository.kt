package com.example.musiceducation.data

import com.example.musiceducation.network.AuthenticateApiService
import com.example.musiceducation.network.UserApiService


interface UserRepository {
    suspend fun register(username: String, password: String, nickname: String):String
    suspend fun login(username: String, password: String):String
    suspend fun getUserInfo(): Any
    suspend fun updateNickname(newNickname: String): String
}
class NetworkUserRepository(
    private val authenticateApiService: AuthenticateApiService,
    private val userApiService: UserApiService
) : UserRepository {
    override suspend fun register(username: String, password: String, nickname: String):String {
        return authenticateApiService.register(username, password, nickname)
    }

    override suspend fun login(username: String, password: String):String {
        return authenticateApiService.login(username, password)
    }

    override suspend fun getUserInfo(): Any {
        return userApiService.getUserInfo()
    }

    override suspend fun updateNickname(newNickname: String): String {
        return authenticateApiService.updateNickname(newNickname)
    }


}
package com.example.musiceducation.data

import com.example.musiceducation.network.AuthenticateApiService
import com.example.musiceducation.network.UserApiService


interface UserRepository {
    suspend fun register(username: String, password: String, nickname: String):String
    suspend fun login(username: String, password: String):String
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


}
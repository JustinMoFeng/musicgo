package com.example.musiceducation.data

import com.example.musiceducation.network.AuthenticateApiService
import com.example.musiceducation.network.ForumApiService
import com.example.musiceducation.network.UserApiService
import com.example.musiceducation.utils.SharedPreferencesManager
import okhttp3.OkHttpClient

interface AppContainer {
    val forumRepository: ForumRepository
    val userRepository: UserRepository
    val baseUrl: String
}

class DefaultAppContainer : AppContainer {
    companion object{
        private const val BASE_URL = "http://192.168.1.6:8080"
    }

    override val baseUrl: String
        get() = "http://192.168.1.6:8080"

    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val requestWithHeaders = originalRequest.newBuilder()
//                    .header("me_token", SharedPreferencesManager.getToken()) // 替换为你想要的头部名和值
                    .build()
                chain.proceed(requestWithHeaders)
            }
            .build()
    }

    private val authenticateApiService: AuthenticateApiService by lazy {
        AuthenticateApiService(BASE_URL, okHttpClient)
    }

    private val userApiService: UserApiService by lazy {
        UserApiService(url = BASE_URL, okHttpClient = okHttpClient)
    }

    private val forumApiService: ForumApiService by lazy {
        ForumApiService(url = BASE_URL, okHttpClient = okHttpClient)
    }

    override val userRepository: UserRepository by lazy {
        NetworkUserRepository(authenticateApiService, userApiService)
    }

    override val forumRepository: ForumRepository by lazy {
        NetworkForumRepository(forumApiService)
    }

}


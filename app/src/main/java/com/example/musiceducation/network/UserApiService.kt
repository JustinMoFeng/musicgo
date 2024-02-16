package com.example.musiceducation.network

import android.util.Log
import com.example.musiceducation.entity.FinalResult
import com.example.musiceducation.entity.User
import com.example.musiceducation.utils.Md5
import com.example.musiceducation.utils.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class UserApiService(
    private val url: String,
    private val okHttpClient: OkHttpClient
) {

    suspend fun getUserInfo(): Any = withContext(
        Dispatchers.IO
    ) {
        try {
            // 获取token
            val token = SharedPreferencesManager.getToken()

            if (token != null) {
                // 创建头部header，加入 me_token = token
                val request = Request.Builder()
                    .url("$url/user/myInfo")
                    .get()
                    .addHeader("me_token", token)
                    .build()

                // 执行请求
                okHttpClient.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        "获取用户信息失败"
                    } else {
                        val res = response.body?.string()
                        Log.d("UserApiService", "getUserInfo: $res")

                        val result = Json.decodeFromString<FinalResult>(res ?: "")
                        val user = Json.decodeFromString<User>(result.data.toString())
                        Log.d("UserApiService", "getUserInfo2: $user")
                        if (result.code == 1) {
                            user
                        } else {
                            result.msg
                        }
                    }
                }
            } else {
                "尚未登录，禁止请求"
            }
        } catch (e: Exception) {
            e.message ?: "请求异常"
        }

    }

}

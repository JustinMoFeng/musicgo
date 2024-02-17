package com.example.musiceducation.network

import android.util.Log
import com.example.musiceducation.entity.DetailForumItem
import com.example.musiceducation.entity.FinalResult
import com.example.musiceducation.entity.ForumItem
import com.example.musiceducation.entity.ResponseForumItem
import com.example.musiceducation.entity.User
import com.example.musiceducation.utils.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request

class ForumApiService(
    private val url: String,
    private val okHttpClient: OkHttpClient
) {
    suspend fun getForumList(pageNum: Int): Any = withContext(
        Dispatchers.IO
    ){
        try {
            // 获取token
            val token = SharedPreferencesManager.getToken()

            if (token != null) {
                // 创建头部header，加入 me_token = token
                val request = Request.Builder()
                    .url("$url/forum/get/$pageNum/10")
                    .get()
                    .addHeader("me_token", token)
                    .build()

                // 执行请求
                okHttpClient.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        "获取论坛列表失败"
                    } else {
                        val res = response.body?.string()
                        Log.d("ForumApiService", "getForumList: $res")

                        val result = Json.decodeFromString<FinalResult>(res ?: "")
                        if (result.code == 1) {
                            val forumList = Json.decodeFromString<ResponseForumItem>(result.data.toString())
                            forumList
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

    suspend fun getForumById(forumId: Int): Any = withContext(
        Dispatchers.IO
    ){
        try {
            // 获取token
            val token = SharedPreferencesManager.getToken()

            if (token != null) {
                // 创建头部header，加入 me_token = token
                val request = Request.Builder()
                    .url("$url/forum/getById/$forumId")
                    .get()
                    .addHeader("me_token", token)
                    .build()

                // 执行请求
                okHttpClient.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        "获取论坛详情失败"
                    } else {
                        val res = response.body?.string()
                        Log.d("ForumApiService", "getForumList: $res")

                        val result = Json.decodeFromString<FinalResult>(res ?: "")
                        if (result.code == 1) {
                            val forumItem = Json.decodeFromString<DetailForumItem>(result.data.toString())
                            forumItem
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
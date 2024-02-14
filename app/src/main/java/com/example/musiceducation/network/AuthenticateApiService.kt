package com.example.musiceducation.network

import android.util.Log
import com.example.musiceducation.entity.FinalResult
import com.example.musiceducation.utils.Md5
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


class AuthenticateApiService (
    private val url: String,
    private val okHttpClient: OkHttpClient
){
    suspend fun register(username: String, password: String, nickname: String):String = withContext(
        Dispatchers.IO
    ){
        val jsonObject = JSONObject().apply {
            put("username", username)
            put("password", Md5.md5(password))
            put("nickname", nickname)
        }
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonObject.toString().toRequestBody(mediaType)


        val request = Request.Builder()
            .url("$url/user/register")
            .post(requestBody)
            .build()

        try {
            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful){
                    val errorResponse = Json.decodeFromString<FinalResult>(response.body!!.string())
                    Log.e("AuthenticateApiService", "Error: ${errorResponse.message}")
                    errorResponse.message
                }else {
                    "true"
                }
            }

        } catch (e: Exception) {
            Log.e("AuthenticateApiService", "Error: ${e.message}")
            e.message!!
        }
    }

    suspend fun login(username: String, password: String):String{
        return "login"
    }

}
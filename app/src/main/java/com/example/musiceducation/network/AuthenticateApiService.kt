package com.example.musiceducation.network

import android.util.Log
import com.example.musiceducation.entity.ErrorResult
import com.example.musiceducation.entity.FinalResult
import com.example.musiceducation.utils.Md5
import com.example.musiceducation.utils.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
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
                    val errorResponse = Json.decodeFromString<ErrorResult>(response.body!!.string())
                    Log.e("AuthenticateApiService", "Error: ${errorResponse.message}")
                    errorResponse.message
                }else {
                    val successResponse = Json.decodeFromString<FinalResult>(response.body!!.string())
                    if(successResponse.code == 1){
                        "true"
                    }else{
                        successResponse.msg
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("AuthenticateApiService", "Exception: ${e.message}")
            e.message!!
        }
    }

    suspend fun login(username: String, password: String):String = withContext(
        Dispatchers.IO
    ){
        val jsonObject = JSONObject().apply {
            put("username", username)
            put("password", Md5.md5(password))
        }
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonObject.toString().toRequestBody(mediaType)


        val request = Request.Builder()
            .url("$url/user/login")
            .post(requestBody)
            .build()

        try {
            okHttpClient.newCall(request).execute().use { response ->
                val res = response.body!!.string()
                Log.d("AuthenticateApiService", "login: $res")
                if (!response.isSuccessful){
                    val errorResponse = Json.decodeFromString<ErrorResult>(res)
                    Log.e("AuthenticateApiService", "Error: ${errorResponse.message}")
                    errorResponse.message
                }else {
                    val successResponse = Json.decodeFromString<FinalResult>(res)
                    if(successResponse.code == 1){
                        SharedPreferencesManager.saveToken(successResponse.data)
                        Log.d("AuthenticateApiService", "login: ${SharedPreferencesManager.getToken()}")
                        "true"
                    }else{
                        successResponse.msg
                    }
                }
            }

        } catch (e: Exception) {
            Log.e("AuthenticateApiService", "Exception: ${e.message}")
            e.message!!
        }
    }

}
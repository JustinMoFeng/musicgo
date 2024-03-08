package com.example.musiceducation.network

import android.util.Log
import com.example.musiceducation.MusicEducationApplication
import com.example.musiceducation.entity.DetailForumItem
import com.example.musiceducation.entity.ErrorResult
import com.example.musiceducation.entity.FileInfo
import com.example.musiceducation.entity.FinalResult
import com.example.musiceducation.entity.ForumItem
import com.example.musiceducation.entity.ResponseForumItem
import com.example.musiceducation.entity.User
import com.example.musiceducation.ui.composables.common.Directory
import com.example.musiceducation.ui.composables.common.DirectoryList
import com.example.musiceducation.utils.Md5
import com.example.musiceducation.utils.SharedPreferencesManager
import com.example.musiceducation.utils.Uri2Path
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File

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
                            Log.d("ForumApiService", "getForumList: ${result.msg}")
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

    suspend fun addForumItem(newForumTitle: String, newBasicForumContent: String, newForumFileList: List<FileInfo>, newForumContentList: List<Directory>): String = withContext(
        Dispatchers.IO
    ){
            val json = Json {
                serializersModule = SerializersModule {
                    polymorphic(Directory::class) {
                        subclass(Directory.InternelLink::class)
                        subclass(Directory.ExternalURILink::class)
                        subclass(Directory.ExternalBookLink::class)
                    }
                }
                classDiscriminator = "type"
            }
            val directoryList = DirectoryList(newForumContentList)
            val client = OkHttpClient()

            // 构建 Multipart 请求体
            val requestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)

        Log.d("ForumApiService", "addForumItem: $newForumContentList")

            JSONObject().apply {
                put("title", newForumTitle)
                put("content", newBasicForumContent)
                put("book_link", json.encodeToString(directoryList))
            }.let {
                requestBody.addFormDataPart("forumBody", it.toString())
            }

        val contentResolver = MusicEducationApplication.instance.contentResolver

            // 添加文件参数
            for (file in newForumFileList) {
                val fille = Uri2Path.getFilePathFromContentUri(file.url, contentResolver )
                val fileMediaType = file.fileType.toMediaTypeOrNull() // 你可能需要根据实际情况调整文件类型
                Log.d("ForumApiService", "addForumItem: $fille")
                val fileRequestBody = fille?.let { RequestBody.create(fileMediaType, it) }
                if (fileRequestBody != null) {
                    requestBody.addFormDataPart("forumFile", file.fileName, fileRequestBody)
                }
            }

            // 构建请求
            val request = Request.Builder()
                .url("$url/forum/add")
                .post(requestBody.build())
                .addHeader("me_token", SharedPreferencesManager.getToken() ?: "")
                .build()

        try {
            // 发送请求
            Log.d("ForumApiService", "addForumItem: 发送请求")
            okHttpClient.newCall(request).execute().use { response ->
                val responseBody = response.body?.string()
                Log.d("ForumApiService", "addForumItem: $responseBody")
                if (response.isSuccessful) {

                    // 处理成功的响应
                    val result = Json.decodeFromString<FinalResult>(responseBody ?: "")
                    if (result.code == 1) {
                        "true"
                    } else {
                        result.msg
                    }
                } else {
                    // 处理失败的响应
                    "尚未登录，禁止请求"
                }
            }

            // 处理响应

        } catch (e: Exception) {
            // 处理异常
            e.message ?: "请求异常"
        }
    }

    suspend fun sendForumCritic(id: Int, forumCriticInput: String): String = withContext(
        Dispatchers.IO
    ){
        val jsonObject = JSONObject().apply {
            put("forum_item_id", id)
            put("critic_content", forumCriticInput)
        }
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val requestBody = jsonObject.toString().toRequestBody(mediaType)


        val request = Request.Builder()
            .url("$url/forum/addCritic")
            .post(requestBody)
            .addHeader("me_token", SharedPreferencesManager.getToken() ?: "")
            .build()

        try {
            okHttpClient.newCall(request).execute().use { response ->
                if (!response.isSuccessful){
                    val errorResponse = Json.decodeFromString<ErrorResult>(response.body!!.string())
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
}
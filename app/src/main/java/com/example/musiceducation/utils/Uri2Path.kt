package com.example.musiceducation.utils

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.MediaColumns
import android.provider.OpenableColumns
import android.util.Log
import com.example.musiceducation.MusicEducationApplication
import java.io.File
import java.io.FileOutputStream

object Uri2Path {


    @SuppressLint("Range")
    fun getFilePathFromContentUri(selectedStringUri: String, contentResolver: ContentResolver): File? {
        try {

            val selectedUri = Uri.parse(selectedStringUri)

            // 查询文件的元数据
            val cursor = contentResolver.query(selectedUri, null, null, null, null)
            cursor?.use {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                cursor.moveToFirst()

                val name = cursor.getString(nameIndex)
                val size = cursor.getLong(sizeIndex)

                // 从ContentResolver中获取输入流
                val inputStream = contentResolver.openInputStream(selectedUri)

                // 创建文件保存路径
                val downloadDir = File(MusicEducationApplication.instance.cacheDir, "Download")
                if (!downloadDir.exists()) {
                    downloadDir.mkdirs()
                }

                // 创建文件
                val file = File(downloadDir, name)
                val fos = FileOutputStream(file)

                // 将输入流写入文件
                inputStream?.use { input ->
                    val buffer = ByteArray(4 * 1024) // buffer size
                    var read: Int
                    while (input.read(buffer).also { read = it } != -1) {
                        fos.write(buffer, 0, read)
                    }
                    fos.flush()
                }

                fos.close()
                inputStream?.close()

                return file
            }
        } catch (e: Exception) {
            Log.e("UriToFile", "Error converting Uri to File: ${e.message}")
        }
        return null
    }

}
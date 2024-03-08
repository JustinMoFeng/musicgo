package com.example.musiceducation.utils

import android.content.Context
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

object KeyValueFileStorage {

    private const val FILE_NAME = "book_catalog_file.txt"

    // 存储键值对
    fun saveKeyValue(context: Context, key: String, value: String) {
        try {
            val fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(fos))
            writer.write("$key=$value")
            writer.newLine()
            writer.close()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // 读取键对应的值
    fun loadValueForKey(context: Context, key: String): String? {
        try {
            val fis = context.openFileInput(FILE_NAME)
            val reader = BufferedReader(InputStreamReader(fis))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val parts = line?.split("=")
                if (parts?.size == 2 && parts[0] == key) {
                    reader.close()
                    fis.close()
                    return parts[1]
                }
            }
            reader.close()
            fis.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    // 清空文件
    fun clearFile(context: Context) {
        try {
            val fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(fos))
            writer.write("") // 写入空字符串以清空文件内容
            writer.close()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
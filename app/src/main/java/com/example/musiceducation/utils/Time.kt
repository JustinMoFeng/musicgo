package com.example.musiceducation.utils

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale

object Time {

    fun formatTime(time: Long): String {
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return format.format(Timestamp(time))
    }

}
package com.example.musiceducation.entity

import kotlinx.serialization.Serializable

@Serializable
data class FileInfo(val fileName: String, val fileType: String, val url: String)
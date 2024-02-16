package com.example.musiceducation.entity

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class FinalResult (
    val code: Int,
    val msg: String,
    val data: String
)
package com.example.musiceducation.entity

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResult (
    val code: Int,
    val message: String,
    val data: String
)
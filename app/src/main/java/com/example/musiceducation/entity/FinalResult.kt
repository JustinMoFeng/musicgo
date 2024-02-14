package com.example.musiceducation.entity

import kotlinx.serialization.Serializable

@Serializable
data class FinalResult (
    val code: Int,
    val message: String,
    val data: Unit
)
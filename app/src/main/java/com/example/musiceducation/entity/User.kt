package com.example.musiceducation.entity

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val id: Int,
    val username: String,
    val password: String,
    val nickname: String,
    val avatar_url: String
)
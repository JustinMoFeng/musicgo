package com.example.musiceducation.entity

import kotlinx.serialization.Serializable

@Serializable
data class ResponseForumItem (
    val size: Int,
    val total: Int,
    val list: List<ForumItem>
)
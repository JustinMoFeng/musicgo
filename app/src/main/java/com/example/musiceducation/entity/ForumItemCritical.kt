package com.example.musiceducation.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForumItemCritical (
    val id: Int,
    @SerialName("critic_time") // Use SerialName to map to the correct JSON field name
    @Serializable(with = TimestampSerializer::class)
    @JsonPrimitive
    val critic_time: Long,
    val critic_content: String,
    val critic_author_name: String,
    val critic_author_avatar: String,
    val forum_item_id: Int
)
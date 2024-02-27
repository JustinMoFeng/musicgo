package com.example.musiceducation.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailForumItem (
    val id: Int,
    val title: String,
    val content: String,
    val author_name: String,
    val author_avatar: String,
    @SerialName("time") // Use SerialName to map to the correct JSON field name
    @Serializable(with = TimestampSerializer::class)
    @JsonPrimitive
    val time: Long,
    val reply: Int,
    val like: Int,
    val criticList: List<ForumItemCritical>,
    val additionalList: List<ForumItemAdditional>,
    val book_link: String
)
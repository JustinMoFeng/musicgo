package com.example.musiceducation.entity

import kotlinx.serialization.Serializable

@Serializable
data class ForumItemAdditional (

    val id: Int,
    val forum_item_id: Int,
    val file_name: String,
    val file_type: String,
    val file_url: String,
    val is_delete: Boolean


)
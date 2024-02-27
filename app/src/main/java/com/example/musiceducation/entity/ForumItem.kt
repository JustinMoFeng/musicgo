package com.example.musiceducation.entity

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale


@Serializable
class ForumItem (
    val id: Int,
    val title: String,
    val content: String,
    val author_name: String,
    val author_avatar: String?,
    @SerialName("time") // Use SerialName to map to the correct JSON field name
    @Serializable(with = TimestampSerializer::class)
    @JsonPrimitive
    val time: Long,
    val reply_num: Int,
    val like_num: Int
)

annotation class JsonPrimitive

@Serializer(forClass = Long::class)
object TimestampSerializer : KSerializer<Long> {
    override fun deserialize(decoder: Decoder): Long {
        return decoder.decodeLong()
    }

    override fun serialize(encoder: Encoder, value: Long) {
        encoder.encodeLong(value)
    }
}
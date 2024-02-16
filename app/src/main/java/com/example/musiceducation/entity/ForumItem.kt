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
    val author: String,
    @SerialName("time") // Use SerialName to map to the correct JSON field name
    @Serializable(with = TimestampSerializer::class)
    val time: Timestamp,
    val reply: Int,
    val like: Int,
    val type: Int
)

@Serializer(forClass = Timestamp::class)
object TimestampSerializer : KSerializer<Timestamp> {
    // Adjust the format based on the timestamp format in your data
    private val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun deserialize(decoder: Decoder): Timestamp {
        val timestampString = decoder.decodeString()
        val date = format.parse(timestampString)
        return Timestamp(date.time)
    }

    override fun serialize(encoder: Encoder, value: Timestamp) {
        val timestampString = format.format(value)
        encoder.encodeString(timestampString)
    }
}
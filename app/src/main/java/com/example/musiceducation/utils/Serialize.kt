package com.example.musiceducation.utils

import kotlinx.serialization.encodeToString

object Serialize {
    fun serialize(obj: Any): String {
        return kotlinx.serialization.json.Json.encodeToString(obj)
    }

    fun serializeList(obj: List<Any>): String {
        return kotlinx.serialization.json.Json.encodeToString(obj)
    }

    inline fun <reified T> deserialize(json: String): T {
        return kotlinx.serialization.json.Json.decodeFromString(json)
    }

    inline fun <reified T> deserializeList(json: String): List<T> {
        return kotlinx.serialization.json.Json.decodeFromString(json)
    }
}
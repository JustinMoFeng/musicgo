package com.example.musiceducation.ui.composables.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@Serializable
@SerialName("directory")
sealed class Directory{
    @Serializable
    @SerialName("internal-link")
    data class InternelLink(val title: String, val bookName: String, val pageIndex: Int, var children: List<Directory> = emptyList()): Directory()

    @Serializable
    @SerialName("external-url-link")
    data class ExternalURILink(val title: String, @SerialName("url")val url: String): Directory()


    @Serializable
    @SerialName("external-book-link")
    data class ExternalBookLink(val title: String, val bookId: String, val pageIndex: Int): Directory()
}

fun main() {
    val json = Json {
        serializersModule = SerializersModule {
            polymorphic(Directory::class) {
                subclass(Directory.InternelLink::class)
                subclass(Directory.ExternalURILink::class)
                subclass(Directory.ExternalBookLink::class)
            }
        }
        classDiscriminator = "type"
    }

    val bookCatalog = listOf(
        Directory.ExternalBookLink("第一单元 乐音与记谱", "选择必修5 音乐基础理论", 12),
        Directory.InternelLink("第二单元 理论基础", "音乐理论入门", 34, listOf(
            Directory.ExternalBookLink("第一单元 乐音与记谱", "选择必修5 音乐基础理论", 12)
        )),
        Directory.ExternalURILink("在线资源", "http://example.com")
    )

    val jsonString = json.encodeToString(bookCatalog)
    println(jsonString)
}
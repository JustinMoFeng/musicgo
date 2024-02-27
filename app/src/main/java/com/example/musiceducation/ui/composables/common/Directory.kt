package com.example.musiceducation.ui.composables.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Directory{
    @Serializable
    data class InternelLink(val title: String, val bookName: String, val pageIndex: Int, var children: List<Directory> = emptyList()): Directory()

    @Serializable
    data class ExternalURILink(val title: String, @SerialName("url")val url: String): Directory()


    @Serializable
    data class ExternalBookLink(val title: String, val bookId: String, val pageIndex: Int): Directory()
}
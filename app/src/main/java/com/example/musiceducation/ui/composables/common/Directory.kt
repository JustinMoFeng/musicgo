package com.example.musiceducation.ui.composables.common

sealed class Directory{
    data class InternelLink(val title: String,val bookName: String, val pageIndex: Int, val children: List<Directory> = emptyList()): Directory()
    data class ExternalURILink(val title: String, val url: String): Directory()

    data class ExternalBookLink(val title: String, val bookId: String, val pageIndex: Int): Directory()
}
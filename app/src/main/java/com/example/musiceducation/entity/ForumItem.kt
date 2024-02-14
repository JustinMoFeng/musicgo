package com.example.musiceducation.entity

import com.example.musiceducation.ui.composables.common.Directory
import java.sql.Timestamp

class ForumItem {
    var id: Int = 0
    var title: String = ""
    var content: String = ""
    var bookLink: List<Directory> = emptyList()
    var externalURILink: List<String> = emptyList()
    var externalFileLink: List<String> = emptyList()
    var author: Int = 0
    var avatar: Int = 0
    var time: Timestamp = Timestamp(System.currentTimeMillis())
    var type: Int = 0 // 0 代表了普通帖子，1 代表了书籍帖子，2 代表了外部链接帖子，3 代表了手动上传文件的帖子

    constructor(id: Int, title: String, content: String, author: Int, avatar: Int, time: Timestamp, type: Int = 0) {
        this.id = id
        this.title = title
        this.content = content
        this.author = author
        this.avatar = avatar
        this.time = time
        this.type = type
    }

    constructor(id: Int, title: String, author: Int, avatar: Int, time: Timestamp, type: Int = 1, bookLink: List<Directory>) {
        this.id = id
        this.title = title
        this.author = author
        this.avatar = avatar
        this.time = time
        this.type = type
        this.bookLink = bookLink
    }

    constructor(id: Int, title: String, author: Int, avatar: Int, time: Timestamp, type: Int, externalLink: List<String>) {
        if(type == 2){
            this.id = id
            this.title = title
            this.author = author
            this.avatar = avatar
            this.time = time
            this.type = type
            this.externalURILink = externalLink
        }else if(type == 3){
            this.id = id
            this.title = title
            this.author = author
            this.avatar = avatar
            this.time = time
            this.type = type
            this.externalFileLink = externalLink
        }
    }




    constructor() {}

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getTitle(): String {
        return title
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun getContent(): String {
        return content
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun getAuthor(): Int {
        return author
    }

    fun setAuthor(author: Int) {
        this.author = author
    }

    fun getAvatar(): Int {
        return avatar
    }

    fun setAvatar(avatar: Int) {
        this.avatar = avatar
    }

    fun getTime(): Timestamp {
        return time
    }

    fun setTime(time: Timestamp) {
        this.time = time
    }

    fun getType(): Int {
        return type
    }

    fun setType(type: Int) {
        this.type = type
    }

    fun getBookLink(): List<Directory> {
        return bookLink
    }

    fun setBookLink(bookLink: List<Directory>) {
        this.bookLink = bookLink
    }

    fun getExternalURILink(): List<String> {
        return externalURILink
    }

    fun setExternalURILink(externalURILink: List<String>) {
        this.externalURILink = externalURILink
    }

    fun getExternalFileLink(): List<String> {
        return externalFileLink
    }

    fun setExternalFileLink(externalFileLink: List<String>) {
        this.externalFileLink = externalFileLink
    }

    override fun toString(): String {
        return "Forumitem(id=$id, title='$title', content='$content', author='$author', avatar=$avatar, time='$time')"
    }


}
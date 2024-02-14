package com.example.musiceducation.entity

import java.sql.Timestamp

class CriticItem {
    var id: Int = 0
    var title: String = ""
    var content: String = ""
    var author: Int = 0
    var avatar: Int = 0
    var time: Timestamp = Timestamp(System.currentTimeMillis())
    var ForumItemId: Int = 0

    constructor(id: Int, title: String, content: String, author: Int, avatar: Int, time: Timestamp, ForumItemId: Int) {
        this.id = id
        this.title = title
        this.content = content
        this.author = author
        this.avatar = avatar
        this.time = time
        this.ForumItemId = ForumItemId
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

    fun getForumItemId(): Int {
        return ForumItemId
    }

    fun setForumItemId(ForumItemId: Int) {
        this.ForumItemId = ForumItemId
    }

    override fun toString(): String {
        return "CriticItem(id=$id, title=$title, content=$content, author=$author, avatar=$avatar, time=$time, ForumItemId=$ForumItemId)"
    }
}
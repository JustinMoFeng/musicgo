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

    override fun toString(): String {
        return "CriticItem(id=$id, title=$title, content=$content, author=$author, avatar=$avatar, time=$time, ForumItemId=$ForumItemId)"
    }
}
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





    constructor() {}

    override fun toString(): String {
        return "Forumitem(id=$id, title='$title', content='$content', author='$author', avatar=$avatar, time='$time')"
    }


}
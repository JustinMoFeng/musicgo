package com.example.musiceducation.entity

class User {
    var id: Int = 0
    var username: String = ""
    var password: String = ""
    var nickname: String = ""
    var avatar: Int = 0

    constructor(id: Int, username: String, password: String, nickname: String, avatar: Int) {
        this.id = id
        this.username = username
        this.password = password
        this.nickname = nickname
        this.avatar = avatar
    }

    constructor() {}

    override fun toString(): String {
        return "user(id=$id, username=$username, password=$password, nickname=$nickname, avatar=$avatar)"
    }


}
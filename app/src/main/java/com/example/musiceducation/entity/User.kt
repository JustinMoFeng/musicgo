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

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getUsername(): String {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun getPassword(): String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getNickname(): String {
        return nickname
    }

    fun setNickname(nickname: String) {
        this.nickname = nickname
    }

    fun getAvatar(): Int {
        return avatar
    }

    fun setAvatar(avatar: Int) {
        this.avatar = avatar
    }

    override fun toString(): String {
        return "user(id=$id, username=$username, password=$password, nickname=$nickname, avatar=$avatar)"
    }


}
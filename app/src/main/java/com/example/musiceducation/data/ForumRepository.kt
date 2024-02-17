package com.example.musiceducation.data

import com.example.musiceducation.network.ForumApiService

interface ForumRepository{
    suspend fun getForumList(pageNum: Int): Any

    suspend fun getForumById(forumId: Int): Any

}

class NetworkForumRepository(
    private val forumApiService: ForumApiService
) : ForumRepository {
    override suspend fun getForumList(pageNum: Int): Any {
        return forumApiService.getForumList(pageNum)
    }

    override suspend fun getForumById(forumId: Int): Any {
        return forumApiService.getForumById(forumId)
    }

}
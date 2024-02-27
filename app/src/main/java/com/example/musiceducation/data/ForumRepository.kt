package com.example.musiceducation.data

import com.example.musiceducation.entity.FileInfo
import com.example.musiceducation.network.ForumApiService
import com.example.musiceducation.ui.composables.common.Directory

interface ForumRepository{
    suspend fun getForumList(pageNum: Int): Any

    suspend fun getForumById(forumId: Int): Any
    suspend fun addForumItem(newForumTitle: String, newBasicForumContent: String, newForumFileList: List<FileInfo>, newForumContentList: List<Directory>): String
    suspend fun sendForumCritic(id: Int, forumCriticInput: String): String


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

    override suspend fun addForumItem(
        newForumTitle: String,
        newBasicForumContent: String,
        newForumFileList: List<FileInfo>,
        newForumContentList: List<Directory>
    ): String {
        return forumApiService.addForumItem(newForumTitle, newBasicForumContent, newForumFileList, newForumContentList)
    }

    override suspend fun sendForumCritic(id: Int, forumCriticInput: String): String {
        return forumApiService.sendForumCritic(id, forumCriticInput)
    }

}
package com.example.musiceducation.ui.viewModels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musiceducation.MusicEducationApplication
import com.example.musiceducation.data.ForumRepository
import com.example.musiceducation.entity.DetailForumItem
import com.example.musiceducation.entity.FileInfo
import com.example.musiceducation.entity.ForumItem
import com.example.musiceducation.entity.ResponseForumItem
import com.example.musiceducation.ui.composables.common.Directory
import com.example.musiceducation.ui.composables.forum.bookName
import kotlinx.coroutines.launch

class ForumViewModel(
    private val forumRepository: ForumRepository
) : ViewModel() {

    var forumBackInfo by mutableStateOf("")

    var forumList by mutableStateOf<List<ForumItem>>(listOf())

    var pageNum by mutableStateOf(1)

    var isComplete by mutableStateOf(false)

    var forumDetail by mutableStateOf(DetailForumItem(0, "", "", "", "", 0, 0, 0, listOf(), listOf(), ""))

    var forumDetailId by mutableStateOf(0)

    var newForumTitle by mutableStateOf("")

    var newBasicForumContent by mutableStateOf("")

    var newforumContentList = mutableStateListOf<Directory>()

    var newForumFileList = mutableStateListOf<FileInfo>()

    var newForumNumberContent = mutableStateListOf<MutableList<MutableList<String>>>()

    var newForumBackInfo = mutableStateOf("")

    var forumCriticInput by mutableStateOf("")

    var criticBackInfo by mutableStateOf("")





    fun getForumById(forumId:Int) {
        viewModelScope.launch {
            forumDetailId = forumId
            val forumResult = forumRepository.getForumById(forumId)
            Log.d("ForumViewModel", "getForumById: $forumResult")
            if(forumResult is String){
                forumBackInfo = forumResult
            }else if(forumResult is DetailForumItem){
                forumDetail = forumResult
            }
        }
    }

    fun getForumList() {
        viewModelScope.launch {
            val forumResult = forumRepository.getForumList(++pageNum)
            Log.d("ForumViewModel", "getForumList: $forumResult")
            if(forumResult is String){
                forumBackInfo = forumResult
            }else if(forumResult is ResponseForumItem){
                if(forumResult.size < 10 || (pageNum - 1) * 10 + forumResult.size == forumResult.total){
                    isComplete = true
                }
                forumList = forumList + forumResult.list
                Log.d("ForumViewModel", "getForumList: $forumList")
            }
        }
    }

    fun getNewForumList(){
        viewModelScope.launch {
            val forumResult = forumRepository.getForumList(1)
            Log.d("ForumViewModel", "getForumList: $forumResult")
            if(forumResult is String){
                forumBackInfo = forumResult
            }else if(forumResult is ResponseForumItem){
//                if(forumResult.size < 10 || (pageNum - 1) * 10 + forumResult.size == forumResult.total){
//                    isComplete = true
//                }else{
//                    pageNum++
//                }
                if(forumResult.size < 10||forumResult.size == forumResult.total){
                    isComplete = true
                }
                forumList = forumResult.list
                Log.d("ForumViewModel", "getForumList: $forumList")
            }
        }
    }

    fun dealDirectory(stList:List<String>):Directory{
        if(stList[0] == "1"){
            return Directory.ExternalBookLink(bookId = stList[1], title = stList[2], pageIndex = stList[3].toInt())
        }else if(stList[0] == "4"){
            return Directory.InternelLink(bookName = stList[1], title = stList[2], pageIndex = stList[3].toInt())
        }else if(stList[0] == "2"){
            return Directory.ExternalURILink(title = stList[1], url = stList[2])
        }
        return Directory.ExternalURILink(title = "未知", url = "未知")
    }

    fun addForumItem() {
        // 把newForumNumberContent 转化为 newForumContentList
        for (i in newForumNumberContent.indices) {
            if(newForumNumberContent[i].isEmpty()){
                continue
            }
            if(newForumNumberContent[i][0][0]=="0"){
                val father = Directory.InternelLink(bookName = newForumNumberContent[i][0][1], title = newForumNumberContent[i][0][2], pageIndex = newForumNumberContent[i][0][3].toInt())
                val childList = mutableListOf<Directory>()
                val tmp = Directory.InternelLink(bookName = newForumNumberContent[i][0][1], title = "原文", pageIndex = newForumNumberContent[i][0][3].toInt())
                childList.add(tmp)
                for (j in 1 until newForumNumberContent[i].size) {
                    val child = dealDirectory(newForumNumberContent[i][j])
                    childList.add(child)
                }
                father.children = childList
                newforumContentList.add(father)
            }else if(newForumNumberContent[i][0][0]=="1"||newForumNumberContent[i][0][0]=="4"){
                val father = Directory.InternelLink(bookName = newForumNumberContent[i][0][1], title = newForumNumberContent[i][0][2], pageIndex = newForumNumberContent[i][0][3].toInt())
                val childList = mutableListOf<Directory>()
                val tmp = Directory.InternelLink(bookName = newForumNumberContent[i][0][1], title = "原文", pageIndex = newForumNumberContent[i][0][3].toInt())
                childList.add(tmp)
                for (j in 1 until newForumNumberContent[i].size) {
                    val child = dealDirectory(newForumNumberContent[i][j])
                    childList.add(child)
                }
                father.children = childList
                newforumContentList.add(father)
            }else {
                val father = Directory.InternelLink(bookName = "未知书籍", title = newForumNumberContent[i][0][1], pageIndex = 0)
                val childList = mutableListOf<Directory>()
                val tmp = Directory.ExternalURILink(title = newForumNumberContent[i][0][1], url = newForumNumberContent[i][0][2])
                childList.add(tmp)
                for (j in 1 until newForumNumberContent[i].size) {
                    val child = dealDirectory(newForumNumberContent[i][j])
                    childList.add(child)
                }
                father.children = childList
                newforumContentList.add(father)

            }
        }

        viewModelScope.launch {
            val forumResult = forumRepository.addForumItem(newForumTitle, newBasicForumContent, newForumFileList.toList(), newforumContentList.toList())
            Log.d("ForumViewModel", "addForumItem: $forumResult")
            newForumBackInfo.value = forumResult
        }
    }

    fun sendForumCritic(id: Int) {
        viewModelScope.launch {
            val forumResult = forumRepository.sendForumCritic(id, forumCriticInput)
            Log.d("ForumViewModel", "sendForumCritic: $forumResult")
            criticBackInfo = forumResult
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicEducationApplication)
                val forumRepository = application.container.forumRepository
                ForumViewModel(forumRepository)
            }
        }
    }
}
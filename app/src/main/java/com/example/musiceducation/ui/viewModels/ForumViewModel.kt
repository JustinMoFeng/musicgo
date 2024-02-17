package com.example.musiceducation.ui.viewModels

import android.util.Log
import androidx.compose.runtime.getValue
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
import com.example.musiceducation.entity.ForumItem
import com.example.musiceducation.entity.ResponseForumItem
import kotlinx.coroutines.launch

class ForumViewModel(
    private val forumRepository: ForumRepository
) : ViewModel() {

    var forumBackInfo by mutableStateOf("")

    var forumList by mutableStateOf<List<ForumItem>>(listOf())

    var pageNum by mutableStateOf(1)

    var isComplete by mutableStateOf(false)

    var forumDetail by mutableStateOf(DetailForumItem(0, "", "", "", "", 0, 0, 0, 0, listOf(), listOf()))

    var forumDetailId by mutableStateOf(0)


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
            val forumResult = forumRepository.getForumList(pageNum)
            Log.d("ForumViewModel", "getForumList: $forumResult")
            if(forumResult is String){
                forumBackInfo = forumResult
            }else if(forumResult is ResponseForumItem){
                if(forumResult.size < 10 || (pageNum - 1) * 10 + forumResult.size == forumResult.total){
                    isComplete = true
                }else{
                    pageNum++
                }
                forumList = forumList + forumResult.list
                Log.d("ForumViewModel", "getForumList: $forumList")
            }
        }
    }

    fun getNewForumList(){
        viewModelScope.launch {
            val forumResult = forumRepository.getForumList(pageNum)
            Log.d("ForumViewModel", "getForumList: $forumResult")
            if(forumResult is String){
                forumBackInfo = forumResult
            }else if(forumResult is ResponseForumItem){
                if(forumResult.size < 10 || (pageNum - 1) * 10 + forumResult.size == forumResult.total){
                    isComplete = true
                }else{
                    pageNum++
                }
                forumList = forumResult.list
                Log.d("ForumViewModel", "getForumList: $forumList")
            }
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
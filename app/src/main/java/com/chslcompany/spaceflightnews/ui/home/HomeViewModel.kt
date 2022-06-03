package com.chslcompany.spaceflightnews.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chslcompany.spaceflightnews.data.model.Post
import com.chslcompany.spaceflightnews.data.repository.MockAPIService
import com.chslcompany.spaceflightnews.data.repository.PostRepository
import com.chslcompany.spaceflightnews.data.repository.PostRepositoryImpl
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val repository : PostRepository = PostRepositoryImpl(MockAPIService)

    private val _listPost = MutableLiveData<List<Post>>()
    val listPost: LiveData<List<Post>>
        get() = _listPost

    init {
        fetchPosts()
    }

    /**
     * Esse método coleta o fluxo do repositorio e atribui
     * o seu valor ao campo _listPost
     */
    private fun fetchPosts() {
        viewModelScope.launch {
           repository.listPosts().collect {
               _listPost.value = it
           }
        }
    }

}
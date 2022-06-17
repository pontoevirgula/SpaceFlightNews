package com.chslcompany.spaceflightnews.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chslcompany.spaceflightnews.data.model.Post
import com.chslcompany.spaceflightnews.data.repository.PostRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PostRepository) : ViewModel() {
    //  private val repository: PostRepository = PostRepositoryImpl(MockAPIService)

    private val _listPost = MutableLiveData<List<Post>>()
    val listPost: LiveData<List<Post>>
        get() = _listPost

    init {
        fetchPosts()
    }

//    private fun fetchPosts() {
//        viewModelScope.launch {
//            repository.getFlowPosts()
//                .catch { e ->
//                    if (e is NullPointerException) throw e
//                    else emit(emptyList())
//                }
//                .collect { posts ->
//                    _listPost.value = posts
//                }
//        }
//    }

    private fun fetchPosts(){
        viewModelScope.launch {
            repository.listPosts().collect{_listPost.value = it}
        }
    }

}
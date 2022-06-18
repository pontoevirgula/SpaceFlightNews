package com.chslcompany.spaceflightnews.core

import com.chslcompany.spaceflightnews.data.model.Post

sealed class PostState{
    object Loading : PostState()
    data class Success(val result: List<Post>) : PostState()
    data class Error(val throwable: Throwable) : PostState()
}

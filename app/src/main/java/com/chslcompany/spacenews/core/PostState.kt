package com.chslcompany.spacenews.core

sealed class PostState<out T: Any>{
    object Loading : PostState<Nothing>()
    data class Success<out T : Any>(val result: T) : PostState<T>()
    data class Error(val throwable: Throwable) : PostState<Nothing>()
}

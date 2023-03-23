package com.chslcompany.spacenews.core

sealed class Resource<T>(
    open val data : T? = null,
    open val error: Throwable? = null
) {
    data class Success<T>(override val data: T?) : Resource<T>(data = data)

    data class Failure<T>(override val data: T?, override val error: Throwable)
        : Resource<T>(data = null, error = error)
}
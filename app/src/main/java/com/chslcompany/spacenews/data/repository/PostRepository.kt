package com.chslcompany.spacenews.data.repository

import com.chslcompany.spacenews.core.Resource
import com.chslcompany.spacenews.data.entities.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun listPosts(category : String) : Flow<Resource<List<Post>>>
    suspend fun listPostTitleContains(category: String, titleContains : String?) : Flow<Resource<List<Post>>>
}
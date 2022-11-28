package com.chslcompany.spaceflightnews.data.repository

import com.chslcompany.spaceflightnews.core.Resource
import com.chslcompany.spaceflightnews.data.entities.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun listPosts(category : String) : Flow<Resource<List<Post>>>
    suspend fun listPostTitleContains(category: String, titleContains : String?) : Flow<Resource<List<Post>>>
}
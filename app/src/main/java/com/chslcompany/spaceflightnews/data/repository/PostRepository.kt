package com.chslcompany.spaceflightnews.data.repository

import com.chslcompany.spaceflightnews.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun listPosts(category : String) : Flow<List<Post>>
    suspend fun listPostTitleContains(category: String, titleContains : String?) : Flow<List<Post>>
}
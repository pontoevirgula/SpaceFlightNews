package com.chslcompany.spaceflightnews.data.repository

import com.chslcompany.spaceflightnews.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    suspend fun listPosts() : Flow<List<Post>>
}
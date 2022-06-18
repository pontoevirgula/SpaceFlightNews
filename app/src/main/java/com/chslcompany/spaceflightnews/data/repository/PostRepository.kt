package com.chslcompany.spaceflightnews.data.repository

import com.chslcompany.spaceflightnews.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun listPosts() : Flow<List<Post>>
}
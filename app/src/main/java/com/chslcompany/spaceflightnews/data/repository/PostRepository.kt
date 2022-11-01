package com.chslcompany.spaceflightnews.data.repository

import com.chslcompany.spaceflightnews.data.entities.network.PostDTO

interface PostRepository {
    suspend fun listPosts(category : String) : List<PostDTO>
    suspend fun listPostTitleContains(category: String, titleContains : String?) : List<PostDTO>
}
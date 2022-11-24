package com.chslcompany.spaceflightnews.data.repository

import com.chslcompany.spaceflightnews.core.Resource
import com.chslcompany.spaceflightnews.data.entities.model.Post
import com.chslcompany.spaceflightnews.data.entities.network.PostDTO

interface PostRepository {
    suspend fun listPosts(category : String) : Resource<List<Post>>
    suspend fun listPostTitleContains(category: String, titleContains : String?) : List<PostDTO>
}
package com.chslcompany.spaceflightnews.data.repository

import com.chslcompany.spaceflightnews.data.entities.network.PostDTO
import com.chslcompany.spaceflightnews.data.service.SpaceFlightNewsService

class PostRepositoryImpl(private val service: SpaceFlightNewsService) : PostRepository {

    override suspend fun listPosts(category: String): List<PostDTO> =
        service.getListPost(category)

    override suspend fun listPostTitleContains(
        category: String,
        titleContains: String?
    ): List<PostDTO> =
        service.getListPostTitleContains(category,titleContains)
    }
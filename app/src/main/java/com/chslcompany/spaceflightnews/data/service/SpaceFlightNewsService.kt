package com.chslcompany.spaceflightnews.data.service

import com.chslcompany.spaceflightnews.data.model.Post
import retrofit2.http.GET

interface SpaceFlightNewsService {

    @GET("articles")
    suspend fun getListPost(): List<Post>
}
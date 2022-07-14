package com.chslcompany.spaceflightnews.data.service

import com.chslcompany.spaceflightnews.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface SpaceFlightNewsService {
    @GET("{type}")
    suspend fun getListPost(
        @Path("type") type : String
    ): List<Post>
}
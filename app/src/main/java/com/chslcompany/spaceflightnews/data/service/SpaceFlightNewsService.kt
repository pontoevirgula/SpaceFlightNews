package com.chslcompany.spaceflightnews.data.service

import com.chslcompany.spaceflightnews.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpaceFlightNewsService {
    @GET("{type}")
    suspend fun getListPost(
        @Path("type") type : String
    ): List<Post>

    @GET("{type}")
    suspend fun getListPostTitleContains(
        @Path("type") type : String,
        @Query("title_contains") titleContains : String?
    ): List<Post>


}
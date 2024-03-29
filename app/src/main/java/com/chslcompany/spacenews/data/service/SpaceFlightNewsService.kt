package com.chslcompany.spacenews.data.service

import com.chslcompany.spacenews.data.entities.network.PostDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpaceFlightNewsService {
    @GET("{type}")
    suspend fun getListPost(
        @Path("type") type : String
    ): List<PostDTO>

    @GET("{type}")
    suspend fun getListPostTitleContains(
        @Path("type") type : String,
        @Query("title_contains") titleContains : String?
    ): List<PostDTO>


}
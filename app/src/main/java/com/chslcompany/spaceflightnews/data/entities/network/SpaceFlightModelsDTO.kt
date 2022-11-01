package com.chslcompany.spaceflightnews.data.entities.network

import com.chslcompany.spaceflightnews.data.entities.model.Launch
import com.chslcompany.spaceflightnews.data.entities.model.Post

data class PostDTO(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String?,
    var launches: List<LaunchDTO> = emptyList()
) {
    fun toPostModel(): Post =
        Post(
            id = id,
            title = title,
            url = url,
            imageUrl = imageUrl,
            summary = summary,
            publishedAt = publishedAt,
            updatedAt = updatedAt,
            launches = launches.toLaunchListModel()
        )
}
fun List<PostDTO>.toPostListModel() : List<Post> =
    this.map {
        it.toPostModel()
    }



data class LaunchDTO(
    val id: String,
    val provider: String
) {
    fun toLaunchModel(): Launch =
        Launch(
            id = id,
            provider = provider
        )
}

fun List<LaunchDTO>.toLaunchListModel(): List<Launch> =
    this.map {
        it.toLaunchModel()
    }


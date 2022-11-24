package com.chslcompany.spaceflightnews.data.entities.network

import com.chslcompany.spaceflightnews.data.entities.db.PostDb
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

    fun toPostDbModel(): PostDb =
        PostDb(
            id = id,
            title = title,
            url = url,
            imageUrl = imageUrl,
            summary = summary,
            publishedAt = publishedAt,
            updatedAt = updatedAt,
            launches = launches.toLaunchDbList()
        )
}


fun List<PostDTO>.toPostListModel() : List<Post> =
    this.map {
        it.toPostModel()
    }

fun List<PostDTO>.toPostDbList() : List<PostDb> =
    this.map {
        it.toPostDbModel()
    }



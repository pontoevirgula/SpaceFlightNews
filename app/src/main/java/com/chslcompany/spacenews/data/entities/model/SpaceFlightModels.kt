package com.chslcompany.spacenews.data.entities.model

data class Post(
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String?,
    var launches: List<Launch> = emptyList()
) {
    fun hasLaunch(): Boolean = launches.isNotEmpty()

    fun getLaunchCount(): Int = launches.size
}

data class Launch(
    val id: String,
    val provider: String
)

data class Search(
    val type : String,
    var titleSearch : String? = null
)

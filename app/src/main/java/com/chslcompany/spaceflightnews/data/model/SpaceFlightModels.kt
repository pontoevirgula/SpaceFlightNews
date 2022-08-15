package com.chslcompany.spaceflightnews.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
    var id: Int = 0,
    var title: String = "",
    var url: String = "",
    var imageUrl: String = "",
    var summary: String = "",
    var publishedAt: String = "",
    var updatedAt: String = "",
    var launches: List<Launch> = emptyList()
) : Parcelable {
    fun hasLaunch(): Boolean = launches.isNotEmpty()

    fun getLaunchCount(): Int = launches.size
}

@Parcelize
data class Launch(
    val id: String,
    val provider: String
):Parcelable

data class Search(
    val type : String,
    var titleSearch : String? = null
)

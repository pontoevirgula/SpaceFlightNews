package com.chslcompany.spaceflightnews.data.entities.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.chslcompany.spaceflightnews.data.entities.model.Launch
import com.chslcompany.spaceflightnews.data.entities.model.Post
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@Entity(tableName = "posts")
data class PostDb(
    @PrimaryKey
    val id: Int,
    val title: String,
    val url: String,
    val imageUrl: String,
    val summary: String,
    val publishedAt: String,
    val updatedAt: String?,
    var launches: List<LaunchDb> = emptyList()
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
fun List<PostDb>.toPostListModel() : List<Post> =
    this.map { postDb ->
        postDb.toPostModel()
    }


@Entity(tableName = "launch")
data class LaunchDb(
    @PrimaryKey
    val id: String,
    val provider: String
) {
    fun toLaunchModel(): Launch =
        Launch(
            id = id,
            provider = provider
        )
}

fun List<LaunchDb>.toLaunchListModel(): List<Launch> =
    this.map {  launchDb ->
        launchDb.toLaunchModel()
    }


class PostDbConverters {
    @TypeConverter
    fun fromString(string: String): List<LaunchDb>? {
//        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
//        val jsonAdapter = moshi.adapter<List<LaunchDb>>()
//        return jsonAdapter.fromJson(string)
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val listMyData = Types.newParameterizedType(List::class.java, LaunchDb::class.java)
        val jsonAdapter = moshi.adapter<List<LaunchDb>>(listMyData)
        return jsonAdapter.fromJson(string)
    }
    @TypeConverter
    fun toString(list: List<LaunchDb>) : String? {
        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val listMyData = Types.newParameterizedType(List::class.java, LaunchDb::class.java)
        val jsonAdapter = moshi.adapter<List<LaunchDb>>(listMyData)
        return jsonAdapter.toJson(list)
    }

    inline fun <reified T> Moshi.parseList(jsonString: String): List<T>? {
        return adapter<List<T>>(Types.newParameterizedType(List::class.java, T::class.java)).fromJson(jsonString)
    }
}
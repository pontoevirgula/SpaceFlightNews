package com.chslcompany.spacenews.data.entities.network

import com.chslcompany.spacenews.data.entities.db.LaunchDb
import com.chslcompany.spacenews.data.entities.model.Launch


data class LaunchDTO(
    val id: String,
    val provider: String
) {
    fun toLaunchModel(): Launch =
        Launch(
            id = id,
            provider = provider
        )

    fun toLaunchDb(): LaunchDb =
        LaunchDb(
            id = id,
            provider = provider
        )
}


fun List<LaunchDTO>.toLaunchListModel(): List<Launch> =
    this.map {
        it.toLaunchModel()
    }

fun List<LaunchDTO>.toLaunchDbList(): List<LaunchDb> =
    this.map {
        it.toLaunchDb()
    }


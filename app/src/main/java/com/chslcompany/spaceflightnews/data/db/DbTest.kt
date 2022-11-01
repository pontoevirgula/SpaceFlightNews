package com.chslcompany.spaceflightnews.data.db

import com.chslcompany.spaceflightnews.data.entities.db.LaunchDb
import com.chslcompany.spaceflightnews.data.entities.db.PostDb
import org.junit.Before

open class DbTest {

    lateinit var dbPostList : List<PostDb>

    @Before
    fun createPostForTest() {
        val postWithoutLaunches = PostDb(
            id = 12781,
            title = "NASA delays Moon landings, says Blue Origin legal tactics partly to blame",
            url = "https://arstechnica.com/science/2021/11/nasa-delays-moon-landings-says-blue-origin-legal-tactics-partly-to-blame/",
            imageUrl = "https://cdn.arstechnica.net/wp-content/uploads/2021/10/51614473753_88c81a224f_k.jpg",
            summary = "We've lost nearly seven months in litigation",
            publishedAt = "2021-11-10T00:34:02.000Z",
            updatedAt = "2021-11-10T05:47:30.161Z",
            launches = emptyList()
        )

        val postWithLaunches = PostDb(
            id = 12782,
            title = "Crew-3 mission cleared for launch",
            url = "https://spacenews.com/crew-3-mission-cleared-for-launch/",
            imageUrl = "https://spacenews.com/wp-content/uploads/2021/11/crew2-chutes.jpg",
            summary = "NASA and SpaceX are ready to proceed with the launch of a commercial crew mission Nov. 10 after overcoming weather and astronaut health issues as well as concerns about the spacecraft’s parachutes.",
            publishedAt = "2021-11-10T09:27:02.000Z",
            updatedAt = "2021-11-10T09:38:23.654Z",
            launches = listOf(
                LaunchDb(
                    id = "0d779392-1a36-4c1e-b0b8-ec11e3031ee6",
                    provider = "Launch Library 2"
                ),
                LaunchDb(
                    id = "0d779392-1a36-4c1e-b0b8-ec11e3031ee6",
                    provider = "Launch Library 2"
                )
            )
        )

        dbPostList = listOf(postWithoutLaunches, postWithLaunches)
    }
}
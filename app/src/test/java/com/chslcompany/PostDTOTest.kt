package com.chslcompany

import com.chslcompany.spaceflightnews.data.entities.model.Post
import com.chslcompany.spaceflightnews.data.entities.network.LaunchDTO
import com.chslcompany.spaceflightnews.data.entities.network.PostDTO
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals
import kotlin.test.assertTrue


@RunWith(JUnit4::class)
class PostDTOTest {

    private val launchDto = LaunchDTO(
            id = "0d779392-1a36-4c1e-b0b8-ec11e3031ee6",
            provider = "Launch Library 2"
    )

    private val postDto = PostDTO(
        id = 12782,
        title = "Crew-3 mission cleared for launch",
        url = "https://spacenews.com/crew-3-mission-cleared-for-launch/",
        imageUrl = "https://spacenews.com/wp-content/uploads/2021/11/crew2-chutes.jpg",
        summary = "NASA and SpaceX are ready to proceed with the launch of a commercial crew mission Nov. 10 after overcoming weather and astronaut health issues as well as concerns about the spacecraft’s parachutes.",
        publishedAt = "2021-11-10T09:27:02.000Z",
        updatedAt = "2021-11-10T09:38:23.654Z",
        launches = listOf(launchDto)
    )

    @Test
    fun `should convert correctly to model entity`(){
       val post : Post = postDto.toPostModel()

        assertTrue(post is Post)
        assertEquals(post.title, postDto.title)
        assertTrue(post.launches.isNotEmpty())
    }
}
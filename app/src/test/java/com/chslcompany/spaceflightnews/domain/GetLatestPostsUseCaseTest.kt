package com.chslcompany.spaceflightnews.domain

import com.chslcompany.spaceflightnews.core.CategoryEnum
import com.chslcompany.spaceflightnews.data.entities.model.Post
import com.chslcompany.spaceflightnews.data.entities.model.Search
import com.chslcompany.spaceflightnews.domain.usecase.GetLatestPostsUseCase
import com.chslcompany.spaceflightnews.setupTestAppComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class GetLatestPostsUseCaseTest : KoinTest {
    private val getLatestPostsUseCase: GetLatestPostsUseCase by inject()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            setupTestAppComponent()
        }

        @AfterClass
        fun tearDown() {
            stopKoin()
        }
    }

    @Test
    fun `should return not null when connect with repository`() {
        runBlocking {
            val result = getLatestPostsUseCase(Search(CategoryEnum.ARTICLES.value))
            assertNotNull(result)
        }
    }

    @Test
    fun `should return not empty when connect with repository`() = runBlocking {
        val result = getLatestPostsUseCase(Search(CategoryEnum.ARTICLES.value))
        assertTrue(result.first().isNotEmpty())
    }

    @Test
    fun `should return right object when connect with repository`() = runBlocking {
        val result = getLatestPostsUseCase(Search(CategoryEnum.ARTICLES.value))
        println(result.first().size)
        assertTrue(result is Flow<List<Post>>)
    }
}
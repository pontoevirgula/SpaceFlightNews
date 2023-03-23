package com.chslcompany.spacenews.domain

import com.chslcompany.spacenews.core.CategoryEnum
import com.chslcompany.spacenews.data.entities.model.Post
import com.chslcompany.spacenews.data.entities.model.Search
import com.chslcompany.spacenews.domain.usecase.GetLatestPostsUseCase
import com.chslcompany.spacenews.setupTestAppComponent
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
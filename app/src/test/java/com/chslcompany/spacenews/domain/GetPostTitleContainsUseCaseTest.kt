package com.chslcompany.spacenews.domain

import com.chslcompany.spacenews.core.CategoryEnum
import com.chslcompany.spacenews.data.entities.model.Search
import com.chslcompany.spacenews.domain.usecase.GetPostTitleContainsUseCase
import com.chslcompany.spacenews.setupTestAppComponent
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

class GetPostTitleContainsUseCaseTest : KoinTest{
    private val getPostTitleContainsUseCase: GetPostTitleContainsUseCase by inject()
    private val type = CategoryEnum.ARTICLES.value
    private val searchTitle = "mars"

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
    fun `should return true when executing search`() {
        runBlocking {
            val result = getPostTitleContainsUseCase(Search(type,searchTitle))
            var isSearchContains = false
            result.first().forEach { post ->
                println(post.title)
                isSearchContains = post.title.lowercase().contains(searchTitle)
            }
            assertTrue(isSearchContains)
        }
    }
}
package com.chslcompany.service

import com.chslcompany.spaceflightnews.core.CategoryEnum
import com.chslcompany.spaceflightnews.data.model.Launch
import com.chslcompany.spaceflightnews.data.model.Post
import com.chslcompany.spaceflightnews.data.service.SpaceFlightNewsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class SpaceFlightNewsServiceTest {
    private lateinit var mockWebServer : MockWebServer
    private lateinit var service : SpaceFlightNewsService


    @Before
    fun createService(){
        val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .build()
            .create(SpaceFlightNewsService::class.java)
    }

    @After
    fun stopService() = mockWebServer.shutdown()

    @Test
    fun `should reach correct endpoint when receive parameter`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setBody("[]"))
        service.getListPost(CategoryEnum.ARTICLES.value)
        val requestArticles = mockWebServer.takeRequest()
        assertEquals(requestArticles.path,"/articles")

        mockWebServer.enqueue(MockResponse().setBody("[]"))
        service.getListPost(CategoryEnum.BLOGS.value)
        val requestBlogs = mockWebServer.takeRequest()
        assertEquals(requestBlogs.path,"/blogs")

        mockWebServer.enqueue(MockResponse().setBody("[]"))
        service.getListPost(CategoryEnum.REPORTS.value)
        val requestReports = mockWebServer.takeRequest()
        assertEquals(requestReports.path,"/reports")
    }

}
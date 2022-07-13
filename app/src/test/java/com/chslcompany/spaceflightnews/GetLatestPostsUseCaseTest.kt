package com.chslcompany.spaceflightnews

import com.chslcompany.spaceflightnews.data.model.Post
import com.chslcompany.spaceflightnews.domain.usecase.GetLatestPostsUseCase
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
    private val urlBaseTest = "https://api.spaceflightnewsapi.net/v3/"


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

//    companion object {
//        private const val URL_BASE_TEST = "https://api.spaceflightnewsapi.net/v3/"
//        private lateinit var koinTest : KoinApplication

//        @BeforeClass
//        @JvmStatic
//        fun setup() {
//            koinTest = startKoin {
//                loadKoinModules(
//                    configureDomainModulesForTest() +
//                            configureDataModulesForTest(URL_BASE_TEST)
//                )
//            }
//        }

//
//        @AfterClass
//        fun tearDown() = stopKoin()
//    }

//    @get:Rule
//    val koinTestRule = KoinTestRule.create {
//        printLogger(Level.DEBUG)
//        modules(
//            module {
//                single {
//                    val factory = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
//
//                    Retrofit.Builder()
//                        .baseUrl("https://api.spaceflightnewsapi.net/v3/")
//                        .addConverterFactory(MoshiConverterFactory.create(factory))
//                        .build()
//                        .create(T::class.java)
//                }
//
//                single<PostRepository> { PostRepositoryImpl(get()) }
//
//                factory { GetLatestPostsUseCase(get()) }
//            }
//        )
//    }
//
//    @get:Rule
//    val mockProvider = MockProviderRule.create { clazz ->
//        // Your way to build a Mock here
//        Mockito.mock(clazz.java)
//    }

//    @Test
//    fun `should inject my components`() {
//
//        // Replace current definition by a Mock
//        val mock = declareMock<GetLatestPostsUseCase>()
//
//        // retrieve mock, same as variable above
//        assertNotNull(get<GetLatestPostsUseCase>())
//    }

    @Test
    fun `should return not null when connect with repository`() {
        runBlocking {
            val result = getLatestPostsUseCase()
            assertNotNull(result)
        }
    }

    @Test
    fun `should return not empty when connect with repository`() = runBlocking {
        val result = getLatestPostsUseCase()
        assertTrue(result.first().isNotEmpty())
    }

    @Test
    fun `should return right object when connect with repository`() = runBlocking {
        val result = getLatestPostsUseCase()
        assertTrue(result is Flow<List<Post>>)
    }
}
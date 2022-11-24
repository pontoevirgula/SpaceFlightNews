package com.chslcompany.spaceflightnews

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chslcompany.spaceflightnews.data.db.DbTest
import com.chslcompany.spaceflightnews.data.db.PostDao
import com.chslcompany.spaceflightnews.data.db.PostDatabase
import com.chslcompany.spaceflightnews.data.entities.db.PostDb
import junit.framework.Assert.assertFalse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class PostDatabaseTest : DbTest(){

    private lateinit var postDatabase: PostDatabase
    private lateinit var dao: PostDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        postDatabase = Room.inMemoryDatabaseBuilder(
            context,
            PostDatabase::class.java).build()
        dao = postDatabase.dao
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        postDatabase.close()
    }

    @Test
    fun must_WritePostsInDatabase_WhenReceivingPostList() {
        lateinit var result: List<PostDb>
        createPostForTest()
        runBlocking {
            result = dao.listPosts().first()
        }
        assertTrue(result.isEmpty())
        runBlocking {
            dao.saveAll(dbPostList)
            result = dao.listPosts().first()
        }
        assertFalse(result.isEmpty())
    }

    @Test
    fun must_ReturnPostListCorrectly_WhenReadDatabase() {
        lateinit var result: PostDb
        createPostForTest()
        runBlocking {
            dao.saveAll(dbPostList)
            result = dao.listPosts().first()[0]
        }
        assertTrue(result.title == dbPostList[0].title)

    }

    @Test
    fun should_CleanDatabase_WhenInvokeClearDb() {
        lateinit var result: List<PostDb>
        createPostForTest()
        runBlocking {
            dao.saveAll(dbPostList)
            dao.clearDb()
            result = dao.listPosts().first()
        }
        assertTrue(result.isEmpty())
    }

}
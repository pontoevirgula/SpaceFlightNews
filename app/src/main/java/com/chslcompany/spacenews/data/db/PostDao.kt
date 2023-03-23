package com.chslcompany.spacenews.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chslcompany.spacenews.data.entities.db.PostDb
import kotlinx.coroutines.flow.Flow


@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE )
    suspend fun saveAll(postDbList : List<PostDb> )

    @Query("SELECT * FROM posts")
    fun listPosts() : Flow<List<PostDb>>

    @Query("DELETE FROM posts")
    suspend fun clearDb()
}

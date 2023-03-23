package com.chslcompany.spacenews.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chslcompany.spacenews.data.entities.db.LaunchDb
import com.chslcompany.spacenews.data.entities.db.PostDb
import com.chslcompany.spacenews.data.entities.db.PostDbConverters


@Database(
    entities = [PostDb::class, LaunchDb::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PostDbConverters::class)
abstract class PostDatabase : RoomDatabase() {
    abstract val dao : PostDao

    companion object{
        @Volatile
        private var INSTANCE: PostDatabase? = null

        fun getInstance(context: Context) : PostDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PostDatabase::class.java,
                        "post_cache_db"
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
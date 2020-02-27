package com.angopa.aad.data.localdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.angopa.aad.androidui.codelabs.paging.reddit.db.RedditPostDao
import com.angopa.aad.utilities.DATABASE_NAME
import com.angopa.aad.workers.SeedCheeseTable
import com.angopa.aad.workers.SeedLinkTableWorker
import com.angopa.aad.workers.SeedPostTableWorker
import com.angopa.aad.workers.SeedTabTableWorker

/**
 * The room database for this app
 */
@Database(
    entities = [Tab::class, Link::class, Post::class, Cheese::class, RedditPost::class],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tabDao(): TabDao
    abstract fun linkDao(): LinkDao
    abstract fun postDao(): PostDao
    abstract fun cheeseDao(): CheeseDao
    abstract fun redditPostDao(): RedditPostDao

    companion object {
        //For singleton implementation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance
                ?: synchronized(this) {
                    instance
                        ?: buildDatabase(
                            context
                        ).also { instance = it }
                }
        }

        //Create and pre-populate the database. see this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedTabTableWorker>().build()
                        val request2 = OneTimeWorkRequestBuilder<SeedLinkTableWorker>().build()
                        val request3 = OneTimeWorkRequestBuilder<SeedPostTableWorker>().build()
                        val request4 = OneTimeWorkRequestBuilder<SeedCheeseTable>().build()
                        WorkManager.getInstance(context).enqueue(request)
                        WorkManager.getInstance(context).enqueue(request2)
                        WorkManager.getInstance(context).enqueue(request3)
                        WorkManager.getInstance(context).enqueue(request4)
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}

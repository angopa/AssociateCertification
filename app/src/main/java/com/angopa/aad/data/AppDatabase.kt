package com.angopa.aad.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.angopa.aad.utilities.DATABASE_NAME
import com.angopa.aad.workers.SeedLinkTableWorker
import com.angopa.aad.workers.SeedTabTableWorker

/**
 * The room database for this app
 */
@Database(entities = [Tab::class, Link::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun tabDao(): TabDao
    abstract fun linkDao(): LinkDao

    companion object {
        //For singleton implementation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance?: buildDatabase(context).also { instance = it }
            }
        }

        //Create and pre-populate the database. see this article for more details:
        // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
        private fun buildDatabase(context: Context) : AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        val request = OneTimeWorkRequestBuilder<SeedTabTableWorker>().build()
                        val request2 = OneTimeWorkRequestBuilder<SeedLinkTableWorker>().build()
                        WorkManager.getInstance(context).enqueue(request)
                        WorkManager.getInstance(context).enqueue(request2)
                    }
                })
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}

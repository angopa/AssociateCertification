package com.angopa.aad.androiddata.codelabs.roomlivedataviewmodel.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 *  Created by Andres Gonzalez on 04/23/2020.
 */
@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        val callback = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.wordDao()?.let { PopulateDatabaseAsync(it).execute() }
            }
        }

        fun getInstance(context: Context): WordRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(callback)
                    .build()
            }.also { INSTANCE = it }
        }

        private class PopulateDatabaseAsync(
            private val wordDao: WordDao
        ) : AsyncTask<Void, Void, Void>() {
            private val words: Array<String> =
                arrayOf("dolphin", "crocodile")

            override fun doInBackground(vararg params: Void?): Void? {
                // Start the app with a clean database every time.
                // Not needed if you only populate the database
                // when it is first created
                wordDao.deleteAll()
                words.forEach {
                    wordDao.insert(Word(it))
                }
                return null
            }

        }
    }
}
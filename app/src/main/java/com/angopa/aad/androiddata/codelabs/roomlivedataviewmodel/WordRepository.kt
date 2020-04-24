package com.angopa.aad.androiddata.codelabs.roomlivedataviewmodel

import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.angopa.aad.androiddata.codelabs.roomlivedataviewmodel.database.Word
import com.angopa.aad.androiddata.codelabs.roomlivedataviewmodel.database.WordDao
import com.angopa.aad.androiddata.codelabs.roomlivedataviewmodel.database.WordRoomDatabase

/**
 *  Created by Andres Gonzalez on 04/23/2020.
 */
class WordRepository(private val context: Context) {
    private val wordDao: WordDao

    var mAllWords: LiveData<List<Word>>? = null

    init {
        val wordRoomDatabase = WordRoomDatabase.getInstance(context)
        wordDao = wordRoomDatabase.wordDao()
        mAllWords = wordDao.getAllWords()
    }


    fun insert(word: Word) {
        insertAsyncTask(wordDao).execute(word)
    }

    private inner class insertAsyncTask(
        private val asyncTaskDao: WordDao
    ) : AsyncTask<Word, Void, Void>() {

        override fun doInBackground(vararg params: Word?): Void? {
            asyncTaskDao.insert(params[0]!!)
            return null
        }

    }
}
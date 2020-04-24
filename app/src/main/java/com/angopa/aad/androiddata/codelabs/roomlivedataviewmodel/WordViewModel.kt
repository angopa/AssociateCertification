package com.angopa.aad.androiddata.codelabs.roomlivedataviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.angopa.aad.androiddata.codelabs.roomlivedataviewmodel.database.Word
import com.angopa.aad.utilities.AppConfiguration

/**
 *  Created by Andres Gonzalez on 04/23/2020.
 */
class WordViewModel : ViewModel() {
    private var wordRepository: WordRepository = WordRepository(AppConfiguration.get().context)

    val allWords: LiveData<List<Word>>?
        get() = wordRepository.mAllWords
}
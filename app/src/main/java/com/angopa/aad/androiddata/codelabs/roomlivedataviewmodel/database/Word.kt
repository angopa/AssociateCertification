package com.angopa.aad.androiddata.codelabs.roomlivedataviewmodel.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  Created by Andres Gonzalez on 04/21/2020.
 */
@Entity(tableName = "word_table")
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String
)
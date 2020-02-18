package com.angopa.aad.data.localdata

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  Created by Andres Gonzalez on 02/18/2020.
 */
@Entity(tableName = "cheese")
data class Cheese(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
)
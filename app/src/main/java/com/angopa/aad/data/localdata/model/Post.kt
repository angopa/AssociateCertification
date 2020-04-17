package com.angopa.aad.data.localdata.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  Created by Andres Gonzalez on 02/05/2020.
 *
 * [Post] represent the links contained in each [Row]
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would no change
 */

@Entity(
    tableName = "post",
    foreignKeys = [],
    indices = []
)
data class Post(
    @ColumnInfo(name = "post_id") val postId: String? = null,
    @ColumnInfo(name = "date") val date: Long? = null,
    @ColumnInfo(name = "caption") val caption: String? = null,
    @ColumnInfo(name = "image_url") val imageUrl: String? = null,
    @ColumnInfo(name = "user_name") val userName: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0
}
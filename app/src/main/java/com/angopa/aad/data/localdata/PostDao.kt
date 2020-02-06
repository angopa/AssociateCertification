package com.angopa.aad.data.localdata

import androidx.paging.DataSource
import androidx.room.*

/**
 *  Created by Andres Gonzalez on 02/05/2020.
 */
@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<Post>)

    // The Int type parameter tells Room to use a PositionalDataSource object.
    @Query("SELECT * FROM post ORDER BY date DESC")
    fun postByDate(): DataSource.Factory<Int, Post>

    @Query("DELETE FROM post")
    suspend fun nukeTable()
}
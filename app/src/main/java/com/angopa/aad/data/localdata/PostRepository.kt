package com.angopa.aad.data.localdata

import androidx.paging.DataSource

/**
 *  Created by Andres Gonzalez on 02/06/2020.
 *
 *  Repositories are created in order to decide where to get the data, if local data is null or old
 *  we can obtain it from remote source. Now we are keeping all local.
 *
 */
class PostRepository private constructor(private val postDao: PostDao) {
    fun getPostByDate(): DataSource.Factory<Int, Post> = postDao.postByDate()

    suspend fun deleteAll() {
        postDao.nukeTable()
    }

    suspend fun createNewPost(post: Post) {
        postDao.insertPost(post)
    }

    companion object {
        //For singleton implementation
        @Volatile
        private var instance: PostRepository? = null

        fun getInstance(postDao: PostDao) = instance ?: synchronized(this) {
            instance ?: PostRepository(postDao).also { instance = it }
        }
    }
}
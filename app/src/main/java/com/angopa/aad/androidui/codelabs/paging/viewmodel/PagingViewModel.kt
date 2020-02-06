package com.angopa.aad.androidui.codelabs.paging.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.angopa.aad.data.localdata.Post
import com.angopa.aad.data.localdata.PostRepository
import com.angopa.aad.utilities.AppConfiguration
import com.angopa.aad.workers.SeedPostTableWorker
import kotlinx.coroutines.launch

/**
 *  Created by Andres Gonzalez on 02/05/2020.
 */
class PagingViewModel(
    private val postRepository: PostRepository
) : ViewModel() {
    private val myPostDataSource: DataSource.Factory<Int, Post> =
        postRepository.getPostByDate()

    val postList: LiveData<PagedList<Post>> =
        LivePagedListBuilder(myPostDataSource, 20).build()

    private val workManager = WorkManager.getInstance(AppConfiguration.get().context)

    fun deleteAll() {
        viewModelScope.launch {
            postRepository.deleteAll()
        }
    }

    fun feedPost() {
        val worker = OneTimeWorkRequestBuilder<SeedPostTableWorker>()
            .build()

        workManager.enqueue(worker)
    }

    fun createNewPost() {
        viewModelScope.launch {
            postRepository.createNewPost(Post(
                "1007",
                System.currentTimeMillis(),
                "Supper!!",
                "https://katv.com/resources/media/29b084ec-8472-4a4f-b803-f8536eb0a60e-large16x9_NWALandTrustProperty.jpg",
                "Yoooooo")
            )
        }

    }
}
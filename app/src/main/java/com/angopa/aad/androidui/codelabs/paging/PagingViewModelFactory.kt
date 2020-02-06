package com.angopa.aad.androidui.codelabs.paging

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.angopa.aad.androidui.codelabs.paging.viewmodel.PagingViewModel
import com.angopa.aad.data.localdata.PostDao
import com.angopa.aad.data.localdata.PostRepository

/**
 *  Created by Andres Gonzalez on 02/06/2020.
 *
 * Factory for creating a [PagingViewModel] with a constructor that takes a [Context],
 * and [PostDao]
 */
class PagingViewModelFactory(
    private val postRepository: PostRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PagingViewModel(postRepository) as T
    }
}
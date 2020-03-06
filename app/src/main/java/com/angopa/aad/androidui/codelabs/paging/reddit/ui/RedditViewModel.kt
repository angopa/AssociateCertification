package com.angopa.aad.androidui.codelabs.paging.reddit.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import com.angopa.aad.utilities.RedditPostRepository

/**
 *  Created by Andres Gonzalez on 02/23/2020.
 */
class RedditViewModel(
    private val repository: RedditPostRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        const val KEY_SUBREDDIT = " subreddit"
        const val DEFAULT_SUBREDDIT = "androiddev"
    }

    init {
        if (!savedStateHandle.contains(KEY_SUBREDDIT)) {
            savedStateHandle.set(KEY_SUBREDDIT, DEFAULT_SUBREDDIT)
        }
    }

    private val repoResult = savedStateHandle.getLiveData<String>(KEY_SUBREDDIT).map {
        repository.postsOfSubreddit(it, 20)
    }

    val posts = repoResult.switchMap { it.pagedList }
    val networkState = repoResult.switchMap { it.networkState }
    val refreshState = repoResult.switchMap { it.refreshState }

    fun refresh() {
        repoResult.value?.refresh?.invoke()
    }

    fun showSubreddit(subreddit: String): Boolean {
        if (savedStateHandle.get<String>(KEY_SUBREDDIT) == subreddit) {
            return false
        }
        savedStateHandle.set(KEY_SUBREDDIT, subreddit)
        return true
    }

    fun retry() {
        repoResult.value?.retry?.invoke()
    }
}
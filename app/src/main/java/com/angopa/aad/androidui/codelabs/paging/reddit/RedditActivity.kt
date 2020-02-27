package com.angopa.aad.androidui.codelabs.paging.reddit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.android.example.paging.pagingwithnetwork.reddit.ServiceLocator
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.paging.reddit.ui.PostAdapter
import com.angopa.aad.androidui.codelabs.paging.reddit.ui.RedditViewModel
import com.angopa.aad.databinding.ActivityRedditBinding
import com.angopa.aad.utilities.RedditPostRepository

/**
 *  Created by Andres Gonzalez on 02/19/2020.
 */
class RedditActivity : BaseActivity() {
    private lateinit var binding: ActivityRedditBinding

    private val model: RedditViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val repoTypeParam = intent.getIntExtra(KEY_REPOSITORY_TYPE, 0)
                val repoType = RedditPostRepository.Type.values()[repoTypeParam]
                val repo = ServiceLocator.instance(this@RedditActivity).getRepository(repoType)
                @Suppress("UNCHECKED_CAST")
                return RedditViewModel(repo, handle) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    override fun getScreenTitle(): Int = R.string.screen_title_reddit

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reddit)
    }

    private fun initAdapter() {
        val glide = GlideApp.with(this)
        val adapter = PostAdapter(glide) {
            model.retry()
        }

    }

    companion object {
        private const val KEY_REPOSITORY_TYPE = "repository_type"
        fun intentFor(context: Context, type: RedditPostRepository.Type): Intent =
            Intent(context, RedditActivity::class.java).run {
                putExtra(KEY_REPOSITORY_TYPE, type.ordinal)
            }
    }
}
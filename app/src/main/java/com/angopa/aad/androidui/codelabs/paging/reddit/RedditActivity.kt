package com.angopa.aad.androidui.codelabs.paging.reddit

import android.content.Context
import android.content.Intent
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.example.paging.pagingwithnetwork.reddit.ServiceLocator
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.paging.reddit.repository.NetworkState
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

    override fun getScreenTitle(): Int = R.string.screen_title_reddit

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityRedditBinding>(
            this,
            R.layout.activity_reddit
        ).apply {
            initAdapter(recyclerView)
            initSwipeToRefresh(swipeRefresh)
            initSearch(input)
        }
    }

    private fun initAdapter(recyclerView: RecyclerView) {
        val glide = GlideApp.with(this)
        val adapter = PostAdapter(glide) {
            model.retry()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        model.posts.observe(this, Observer {
            adapter.submitList(it) {
                // Workaround for an issue where RecyclerView incorrectly uses the loading / spinner
                // item added to the end of the list as an anchor during initial load.
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = layoutManager.findFirstCompletelyVisibleItemPosition()
                if (position != RecyclerView.NO_POSITION) {
                    recyclerView.scrollToPosition(position)
                }
            }
        })

        model.networkState.observe(this, Observer {
            adapter.setNetworkState(it)
        })
    }

    private fun initSwipeToRefresh(swipeRefresh: SwipeRefreshLayout) {
        model.refreshState.observe(this, Observer {
            swipeRefresh.isRefreshing = it == NetworkState.LOADING
        })
        swipeRefresh.setOnRefreshListener {
            model.refresh()
        }
    }

    private fun initSearch(input: EditText) {
        input.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateSubredditFromInput(input)
                true
            } else {
                false
            }
        }

        input.setOnKeyListener { _, keyCode, event ->
            if (event.action == ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                updateSubredditFromInput(input)
                true
            } else {
                false
            }
        }
    }

    private fun updateSubredditFromInput(input: EditText) {
        input.text.trim().toString().let {
            if (it.isNotEmpty()) {
                if (model.showSubreddit(it)) {
                    binding.recyclerView.scrollToPosition(0)
                    (binding.recyclerView.adapter as PostAdapter).submitList(null)
                }
            }
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
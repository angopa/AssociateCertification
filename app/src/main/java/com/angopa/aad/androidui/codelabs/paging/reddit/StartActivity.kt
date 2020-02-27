package com.angopa.aad.androidui.codelabs.paging.reddit

import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityStartRedditBinding
import com.angopa.aad.utilities.RedditPostRepository

/**
 *  Created by Andres Gonzalez on 02/18/2020.
 */
class StartActivity : BaseActivity() {
    private lateinit var binding: ActivityStartRedditBinding

    override fun getScreenTitle(): Int = R.string.screen_title_reddit

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityStartRedditBinding>(
            this,
            R.layout.activity_start_reddit
        ).apply {
            withDatabase.setOnClickListener {
                show(RedditPostRepository.Type.DB)
            }

            networkOnly.setOnClickListener {
                show(RedditPostRepository.Type.IN_MEMORY_BY_ITEM)
            }

            networkOnlyWithPageKey.setOnClickListener {
                show(RedditPostRepository.Type.IN_MEMORY_BY_PAGE)
            }
        }
    }

    private fun show(type: RedditPostRepository.Type) {
        val intent = RedditActivity.intentFor(this, type)
        startActivity(intent)
    }
}
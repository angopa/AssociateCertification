package com.angopa.aad.androidui.codelabs.paging

import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.paging.social.PagingViewModel
import com.angopa.aad.databinding.ActivityCreatePostBinding
import com.angopa.aad.utilities.InjectorUtils

/**
 *  Created by Andres Gonzalez on 02/06/2020.
 */
class CreatePostActivity : BaseActivity() {
    private lateinit var binding: ActivityCreatePostBinding

    private val pagingViewModel: PagingViewModel by viewModels {
        InjectorUtils.providePagingViewModelFactory(this@CreatePostActivity)
    }

    override fun getScreenTitle(): Int = R.string.insert_post_screen_title

    override fun getBindingComponent() {
        binding =
            DataBindingUtil.setContentView(this@CreatePostActivity, R.layout.activity_create_post)

        binding.createPostButton.setOnClickListener {
            pagingViewModel.createNewPost()
        }
    }
}
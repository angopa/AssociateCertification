package com.angopa.aad.androidui.codelabs.paging

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.paging.adapter.PostAdapter
import com.angopa.aad.androidui.codelabs.paging.viewmodel.PagingViewModel
import com.angopa.aad.data.localdata.Post
import com.angopa.aad.databinding.ActivityPagingBinding
import com.angopa.aad.utilities.AppConfiguration
import com.angopa.aad.utilities.DialogFactory
import com.angopa.aad.utilities.InjectorUtils
import timber.log.Timber

/**
 *  Created by Andres Gonzalez on 02/05/2020.
 */
class SocialExamplePagingActivity : BaseActivity(), PostAdapter.PostActionsListener {
    private lateinit var binding: ActivityPagingBinding

    private val pagingViewModel: PagingViewModel by viewModels {
        InjectorUtils.providePagingViewModelFactory(this@SocialExamplePagingActivity)
    }

    private lateinit var postAdapter: PostAdapter

    private lateinit var dataset: ArrayList<Post>

    override fun getScreenTitle(): Int = R.string.paging_screen_title

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityPagingBinding>(
            this,
            R.layout.activity_paging
        ).apply {
            dataset = ArrayList()

            postAdapter =
                PostAdapter(
                    this@SocialExamplePagingActivity,
                    this@SocialExamplePagingActivity,
                    dataset
                )

            recyclerView.layoutManager = LinearLayoutManager(this@SocialExamplePagingActivity)
            recyclerView.adapter = postAdapter

            addPostFab.setOnClickListener {
                createNewPostActivity()
            }
        }

        pagingViewModel.postList.observe(this, Observer {
            postAdapter.updateData(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_paging, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.option_delete -> {
                pagingViewModel.deleteAll()
                true
            }
            R.id.option_insert -> {
                pagingViewModel.feedPost()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    private fun createNewPostActivity() {
        val intent = Intent(this, CreatePostActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                Intent.FLAG_ACTIVITY_NEW_TASK

        startActivity(intent)
    }

    @SettingOption
    private var option: Int = REPORT

    override fun settingAction(postId: String) {
        showAlertDialog(
            DialogFactory(AppConfiguration.get())
                .createSettingOptionsDialog(
                    this@SocialExamplePagingActivity,
                    object : DialogFactory.SettingDialogOptionListener {
                        override fun optionSelected(@SettingOption which: Int) {
                            option = which
                            Timber.d("Options Selected $option from post $postId")
                        }
                    })
        )
    }
}
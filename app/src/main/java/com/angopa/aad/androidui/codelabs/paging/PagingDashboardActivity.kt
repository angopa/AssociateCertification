package com.angopa.aad.androidui.codelabs.paging

import android.content.Intent
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.androidui.codelabs.paging.cheesefactory.CheeseFactoryPagingActivity
import com.angopa.aad.androidui.codelabs.paging.reddit.StartActivity
import com.angopa.aad.androidui.codelabs.paging.social.SocialExamplePagingActivity
import com.angopa.aad.databinding.ActivityPagingDashboardBinding

/**
 *  Created by Andres Gonzalez on 02/18/2020.
 */
class PagingDashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityPagingDashboardBinding

    override fun getScreenTitle(): Int = R.string.screen_title_paging_dashboard

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityPagingDashboardBinding>(
            this,
            R.layout.activity_paging_dashboard
        ).apply {
            socialExample.setOnClickListener {
                startActivity(
                    Intent(
                        this@PagingDashboardActivity,
                        SocialExamplePagingActivity::class.java
                    )
                )
            }

            cheeseFactory.setOnClickListener {
                startActivity(
                    Intent(
                        this@PagingDashboardActivity,
                        CheeseFactoryPagingActivity::class.java
                    )
                )
            }

            redditExample.setOnClickListener {
                startActivity(
                    Intent(
                        this@PagingDashboardActivity,
                        StartActivity::class.java
                    )
                )
            }
        }

    }
}
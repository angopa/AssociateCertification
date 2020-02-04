package com.angopa.aad.androidui.codelabs.recyclerview

import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityRecyclerViewBinding

/**
 *  Created by Andres Gonzalez on 02/03/2020.
 */
class RecyclerViewActivity : BaseActivity() {
    private lateinit var binding: ActivityRecyclerViewBinding
    override fun getScreenTitle(): Int = R.string.label_title_recycler_view

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView(
            this@RecyclerViewActivity,
            R.layout.activity_recycler_view
        )
    }

}
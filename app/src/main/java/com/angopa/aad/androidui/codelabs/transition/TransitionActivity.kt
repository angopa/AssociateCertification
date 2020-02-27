package com.angopa.aad.androidui.codelabs.transition

import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityTransitionBinding

/**
 *  Created by Andres Gonzalez on 02/27/2020.
 */
class TransitionActivity : BaseActivity() {
    private lateinit var binding: ActivityTransitionBinding

    override fun getScreenTitle(): Int = R.string.screen_title_transition

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transition)
    }
}
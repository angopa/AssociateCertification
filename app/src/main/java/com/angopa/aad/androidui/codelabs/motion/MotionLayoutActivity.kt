package com.angopa.aad.androidui.codelabs.motion

import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityMotionLayoutBinding

/**
 *  Created by Andres Gonzalez on 02/03/2020.
 */
class MotionLayoutActivity : BaseActivity() {
    private lateinit var binding: ActivityMotionLayoutBinding

    override fun getScreenTitle(): Int = R.string.label_title_motion_layout


    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_motion_layout)
    }

}
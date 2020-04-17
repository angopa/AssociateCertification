package com.angopa.aad.androidui.codelabs.navigationdrawer

import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityNavigationDrawerBinding

/**
 *  Created by Andres Gonzalez on 03/18/2020.
 */
class NavigationDrawerActivity : BaseActivity() {
    private lateinit var binding: ActivityNavigationDrawerBinding
    override fun getScreenTitle(): Int = R.string.label_title_navigation_drawer

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation_drawer)
    }

}
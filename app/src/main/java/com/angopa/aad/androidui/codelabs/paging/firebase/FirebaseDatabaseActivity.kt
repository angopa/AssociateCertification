package com.angopa.aad.androidui.codelabs.paging.firebase

import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityFirebaseDatabaseBinding

/**
 *  Created by Andres Gonzalez on 03/17/2020.
 */
class FirebaseDatabaseActivity : BaseActivity() {
    private lateinit var binding: ActivityFirebaseDatabaseBinding

    override fun getScreenTitle(): Int = R.string.label_title_firebase_realtime_database

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_firebase_database)
    }

}
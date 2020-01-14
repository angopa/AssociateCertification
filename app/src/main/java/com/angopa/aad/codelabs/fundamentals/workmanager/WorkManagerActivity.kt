package com.angopa.aad.codelabs.fundamentals.workmanager

import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityWorkManagerBinding

class WorkManagerActivity : BaseActivity() {
    private lateinit var binding: ActivityWorkManagerBinding

    override fun getScreenTitle(): Int {
        return R.string.work_manager_screen_title
    }

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_work_manager)
    }
}
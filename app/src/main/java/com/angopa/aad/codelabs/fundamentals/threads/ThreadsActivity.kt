package com.angopa.aad.codelabs.fundamentals.threads

import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityThreadsBinding

class ThreadsActivity : BaseActivity() {

    private lateinit var binding: ActivityThreadsBinding

    override fun getScreenTitle(): Int = R.string.thread_screen_title

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_threads)
    }

}
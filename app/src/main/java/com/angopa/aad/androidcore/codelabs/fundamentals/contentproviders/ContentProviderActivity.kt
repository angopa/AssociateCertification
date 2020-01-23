package com.angopa.aad.androidcore.codelabs.fundamentals.contentproviders

import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityContentProviderBinding

class ContentProviderActivity : BaseActivity() {

    private lateinit var binding: ActivityContentProviderBinding

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_content_provider)
    }

    override fun getScreenTitle(): Int = R.string.label_title_content_providers
}

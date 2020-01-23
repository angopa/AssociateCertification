package com.angopa.aad.androidcore.codelabs.notification

import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityNotificationsBinding

class NotificationCodelabActivity : BaseActivity() {

    private lateinit var binding: ActivityNotificationsBinding

    override fun getScreenTitle(): Int = R.string.notification_activity_title

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notifications)
    }
}
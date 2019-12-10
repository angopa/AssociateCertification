package com.angopa.aad

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import com.angopa.aad.utilities.*
import com.angopa.aad.utilities.localization.LocaleManager
import timber.log.Timber

abstract class CoreApplication : Application() {

    abstract fun initializeAppConfiguration()

    override fun onCreate() {
        super.onCreate()

        initializeAppConfiguration()

        if (!AppConfiguration.get().isReleaseBuild()) {
            Timber.plant(Timber.DebugTree())
        }

        configureDefaultLocale()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannels()
        }
    }

    @Suppress("UNUSED_VARIABLE")
    private fun configureDefaultLocale() {
        val storage = Storage.getInstance(this)
        val localeMainActivity = LocaleManager.init(this, storage)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannels() {
        var channel = NotificationChannel(FOREGROUND_NOTIFICATION_CHANNEL_ID, "My Background Service", NotificationManager.IMPORTANCE_NONE)
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)

        channel = NotificationChannel(REGULAR_NOTIFICATION_CHANNEL_ID, "Regular Notifications", NotificationManager.IMPORTANCE_DEFAULT)
        service.createNotificationChannel(channel)

        channel = NotificationChannel(IMPORTANT_NOTIFICATION_CHANNEL_ID, "Important Notifications", NotificationManager.IMPORTANCE_HIGH)
        service.createNotificationChannel(channel)

        channel = NotificationChannel(PRIMARY_CHANNEL_ID, "Job Service notification", NotificationManager.IMPORTANCE_HIGH)
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        service.createNotificationChannel(channel)
    }

}
package com.angopa.aad

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.util.Log
import androidx.annotation.RequiresApi
import com.angopa.aad.utilities.*
import com.angopa.aad.utilities.localization.LocaleManager
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import io.fabric.sdk.android.Fabric
import timber.log.Timber
import timber.log.Timber.DebugTree

abstract class CoreApplication : Application() {

    abstract fun initializeAppConfiguration()

    override fun onCreate() {
        super.onCreate()

        val crashlyticsEnabled = BuildConfig.CRASHLYTICS_ENABLED

        if (crashlyticsEnabled) {
            Fabric.with(this, Crashlytics())
        }

        initializeAppConfiguration()

        if (!AppConfiguration.get().isReleaseBuild()) {
            configureDebugLogging(crashlyticsEnabled)
        }

        configureDefaultLocale()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannels()
        }

        configureFirebase()
    }

    @Suppress("UNUSED_VARIABLE")
    private fun configureDefaultLocale() {
        val storage = Storage.getInstance(this)
        val localeMainActivity = LocaleManager.init(this, storage)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannels() {
        var channel = NotificationChannel(
            FOREGROUND_NOTIFICATION_CHANNEL_ID,
            "My Background Service",
            NotificationManager.IMPORTANCE_NONE
        )
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)

        channel = NotificationChannel(
            REGULAR_NOTIFICATION_CHANNEL_ID,
            "Regular Notifications",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        service.createNotificationChannel(channel)

        channel = NotificationChannel(
            IMPORTANT_NOTIFICATION_CHANNEL_ID,
            "Important Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        service.createNotificationChannel(channel)

        channel = NotificationChannel(
            PRIMARY_CHANNEL_ID,
            "Job Service notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.enableVibration(true)
        service.createNotificationChannel(channel)
    }

    private fun configureFirebase() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Timber.e("getInstanceId failed")
                }

                val token = task.result?.token
                Timber.d("Token: %s", token)
            })
    }

    private fun configureDebugLogging(crashlyticsEnabled: Boolean) {
        Timber.plant(DebugTree())
        if (crashlyticsEnabled) {
            Timber.plant(object : Timber.Tree() {
                override fun log(
                    priority: Int,
                    tag: String?,
                    message: String,
                    t: Throwable?
                ) { // Nothing to log.
                    if (message == null) {
                        return
                    }
                    // Don't log verbose messages.
                    if (priority < Log.DEBUG) {
                        return
                    }
                    // Don't log StrictMode violations.
                    if ("StrictMode" == tag) {
                        return
                    }
                    /*
                     * Remove extraneous whitespace when logging to fit as much as possible in the 64KB log limit.
                     * See https://docs.fabric.io/android/crashlytics/enhanced-reports.html?#custom-logging
                     */
                    Crashlytics.log(message.trim { it <= ' ' }.replace("\\s+".toRegex(), " "))
                }
            })
        }
        // Enable strict mode checks.
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )

        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build()
        )
    }
}
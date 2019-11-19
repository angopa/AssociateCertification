package com.angopa.aad

import android.app.Application
import com.angopa.aad.utilities.AppConfiguration
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
    }

    @Suppress("UNUSED_VARIABLE")
    private fun configureDefaultLocale() {
        val storage = Storage(this)
        val localeMainActivity = LocaleManager.init(this, storage)
    }
}
package com.angopa.aad

import android.app.Application
import com.angopa.aad.utilities.AppConfiguration
import timber.log.Timber

abstract class CoreApplication : Application() {

    abstract fun initializeAppConfiguration()

    override fun onCreate() {
        super.onCreate()

        initializeAppConfiguration()

        if (!AppConfiguration.get().isReleaseBuild()) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
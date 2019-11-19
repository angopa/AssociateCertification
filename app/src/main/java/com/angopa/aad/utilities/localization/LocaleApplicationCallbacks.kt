package com.angopa.aad.utilities.localization

import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration

internal class LocaleApplicationCallbacks(
    private val context: Context,
    private val localeManager: LocaleManager
) : ComponentCallbacks {

    override fun onConfigurationChanged(newConfig: Configuration) {
        localeManager.setLocaleInternal(context)
    }

    override fun onLowMemory() {}
}
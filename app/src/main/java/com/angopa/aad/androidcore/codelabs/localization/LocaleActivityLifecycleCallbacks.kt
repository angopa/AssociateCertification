package com.angopa.aad.androidcore.codelabs.localization

import android.app.Activity
import android.app.Application
import android.os.Bundle

internal class LocaleActivityLifecycleCallbacks(
    private val localeManager: LocaleManager
) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        localeManager.setLocaleInternal(activity)
        localeManager.resetActivityTitle(activity)
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityDestroyed(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}
}
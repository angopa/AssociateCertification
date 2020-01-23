package com.angopa.aad.androidcore.codelabs.fundamentals.activity

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

class MyObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun initializeEventBus() {
        Timber.d("initializeEventBus when Lifecycle: ON_START")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun connectListener() {
        Timber.d("connectListener when Lifecycle: ON_RESUME")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun disconnectListener() {
        Timber.d("disconnectListener when Lifecycle: ON_PAUSE")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun disconnectEventBus() {
        Timber.d("disconnectEventBus when Lifecycle: ON_STOP")
    }
}
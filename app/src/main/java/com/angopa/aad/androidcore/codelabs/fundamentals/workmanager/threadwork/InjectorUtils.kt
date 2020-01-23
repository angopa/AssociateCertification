package com.angopa.aad.androidcore.codelabs.fundamentals.workmanager.threadwork

import android.content.Context

object InjectorUtils {
    fun getThreadWorkViewModelFactory(context: Context): ThreadWorkViewModelFactory = ThreadWorkViewModelFactory(context)
}
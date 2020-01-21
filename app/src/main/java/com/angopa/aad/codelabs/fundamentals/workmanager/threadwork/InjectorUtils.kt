package com.angopa.aad.codelabs.fundamentals.workmanager.threadwork

import android.content.Context

object InjectorUtils {
    fun getThreadWorkViewModelFactory(context: Context): ThreadWorkViewModelFactory = ThreadWorkViewModelFactory(context)
}
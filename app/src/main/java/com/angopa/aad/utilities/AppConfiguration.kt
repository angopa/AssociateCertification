package com.angopa.aad.utilities

import android.content.Context

class AppConfiguration private constructor(
    val context: Context,
    private val releaseBuild: Boolean
) {
    fun isReleaseBuild() = releaseBuild

    companion object {
        @Volatile
        private var instance: AppConfiguration? = null

        fun getInstance(context: Context, releaseBuild: Boolean) = instance ?: synchronized(this) {
            instance ?: AppConfiguration(context, releaseBuild).also { instance = it }
        }

        fun get() : AppConfiguration = instance!!
    }

}
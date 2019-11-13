package com.angopa.aad.utilities

class AppConfiguration private constructor(
    private val releaseBuild: Boolean
) {
    fun isReleaseBuild() = releaseBuild

    companion object {
        @Volatile
        private var instance: AppConfiguration? = null

        fun getInstance(releaseBuild: Boolean) = instance ?: synchronized(this) {
            instance ?: AppConfiguration(releaseBuild).also { instance = it }
        }

        fun get() : AppConfiguration = instance!!
    }

}
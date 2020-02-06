package com.angopa.aad

import com.angopa.aad.utilities.AppConfiguration

class Application : CoreApplication() {

    override fun initializeAppConfiguration() {
        AppConfiguration.getInstance(this, false)
    }
}
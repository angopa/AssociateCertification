package com.angopa.aad.ui

import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import com.angopa.aad.R

class MainActivity : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Enables Always-on
        setAmbientEnabled()
    }
}

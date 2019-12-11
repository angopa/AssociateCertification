package com.angopa.aad.codelabs.fundamentals.broadcast

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.view.View
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityBroadcastReceiverBinding
import com.angopa.aad.utilities.localization.LocaleManager

class BroadcastReceiverActivity : BaseActivity() {

    private lateinit var binding: ActivityBroadcastReceiverBinding

    private var broadcastReceiverWiFi: BroadcastReceiver? = null

    private var broadcastReceiverRotation: BroadcastReceiver? = null

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityBroadcastReceiverBinding>(
            this, R.layout.activity_broadcast_receiver
        ).apply {
            startButton.setOnClickListener { view ->
                startBroadcastReceiver()
                view.isEnabled = false
                binding.stopButton.isEnabled = true
            }

            stopButton.setOnClickListener { view ->
                stopBroadcastReceiver()
                view.isEnabled = false
                binding.startButton.isEnabled = true

            }
        }
    }

    override fun getScreenTitle(): Int = R.string.label_broadcast_receiver_toolbar_title

    fun startBroadcastForOrientationChanges(view: View) {
        broadcastReceiverRotation = AsyncPlaneModeBroadcastReceiver()

        val intentFilter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(broadcastReceiverRotation, intentFilter)
        view.isEnabled = false
        displayToastMessage(getString(R.string.generic_label_start))
    }

    fun sendMessageToWearable(view: View) {
        Intent().also { intent ->
            intent.action = "com.angopa.aad.MESSAGE"
            intent.putExtra("data", "Message from the app!")
            intent.setPackage("com.angopa.aad")
            sendBroadcast(intent)
        }
    }

    override fun onDestroy() {
        if (broadcastReceiverWiFi != null) {
            unregisterReceiver(broadcastReceiverWiFi)
            broadcastReceiverWiFi = null
            displayToastMessage(getString(R.string.label_broadcast_receiver_stop_message))
        }

        if (broadcastReceiverRotation != null) {
            unregisterReceiver(broadcastReceiverRotation)
            broadcastReceiverRotation = null
        }
        super.onDestroy()
    }

    private fun startBroadcastReceiver() {
        broadcastReceiverWiFi = WiFiBroadcastReceiver()

        val intentFilter = IntentFilter().apply {
            addAction("android.net.wifi.WIFI_STATE_CHANGED")
            addAction("android.net.wifi.STATE_CHANGED")
        }

        registerReceiver(broadcastReceiverWiFi, intentFilter)
        displayToastMessage(getString(R.string.label_broadcast_receiver_start_message))
    }

    private fun stopBroadcastReceiver() {
        if (broadcastReceiverWiFi != null) {
            unregisterReceiver(broadcastReceiverWiFi)
            broadcastReceiverWiFi = null
            displayToastMessage(getString(R.string.label_broadcast_receiver_stop_message))
        }
    }
}
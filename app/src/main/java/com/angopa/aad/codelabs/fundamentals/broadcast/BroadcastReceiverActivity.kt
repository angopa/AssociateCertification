package com.angopa.aad.codelabs.fundamentals.broadcast

import android.content.BroadcastReceiver
import android.content.IntentFilter
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityBroadcastReceiverBinding

class BroadcastReceiverActivity : BaseActivity() {

    private lateinit var binding: ActivityBroadcastReceiverBinding

    private var broadcastReceiver: BroadcastReceiver? = null

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

    override fun onDestroy() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver)
            broadcastReceiver = null
            displayToastMessage(getString(R.string.label_broadcast_receiver_stop_message))
        }
        super.onDestroy()
    }

    private fun startBroadcastReceiver() {
        broadcastReceiver = BroadcastReceiverWiFi()

        val intentFilter = IntentFilter().apply {
            addAction("android.net.wifi.WIFI_STATE_CHANGED")
            addAction("android.net.wifi.STATE_CHANGED")
        }

        registerReceiver(broadcastReceiver, intentFilter)
        displayToastMessage(getString(R.string.label_broadcast_receiver_start_message))
    }

    private fun stopBroadcastReceiver() {
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver)
            broadcastReceiver = null
            displayToastMessage(getString(R.string.label_broadcast_receiver_stop_message))
        }
    }
}
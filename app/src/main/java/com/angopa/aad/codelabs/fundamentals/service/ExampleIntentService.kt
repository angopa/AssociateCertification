package com.angopa.aad.codelabs.fundamentals.service

import android.app.IntentService
import android.content.Intent
import android.widget.Toast
import timber.log.Timber

/**
 * A constructor is required, and must call the super [android.app.IntentService.IntentService]
 * constructor with a name for the worker thread.
 *
 * This is the best option if you don't require that your service handle multiple requests simultaneously.
 */
class ExampleIntentService : IntentService("ExampleIntentService") {

    /**
     * The intent service calls this method from the default worker thread with the intent that
     * started the service. When this method returns, IntentService stop the service, as appropriate
     */
    override fun onHandleIntent(intent: Intent?) {
        // Normally we would do some work here. For our example, we sleep 5 seconds
        try {
            Timber.d("Sleep Service ${intent.toString()}")
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            // Restore interrupt status
            Thread.currentThread().interrupt()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service Starting: $startId", Toast.LENGTH_SHORT).show()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service done", Toast.LENGTH_SHORT).show()
    }
}
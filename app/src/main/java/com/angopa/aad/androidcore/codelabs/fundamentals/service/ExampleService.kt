package com.angopa.aad.androidcore.codelabs.fundamentals.service

import android.app.Service
import android.content.Intent
import android.os.*
import android.widget.Toast
import timber.log.Timber

/**
 * Extends base class for all Services, the service uses your application's main thread by default,
 * which can slow the performance of any activity that the application is running.
 */
class ExampleService : Service() {

    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    // Handler that receives messages from the thread
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            // Normally we would do some work here, for our sample we just sleep 5 seconds
            try {
                Timber.d("Thread ${msg.arg1} sleeping")
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                // Restore interrupt status
                Thread.currentThread().interrupt()
            }

            // Stop the service using the startId, so the we don't stop the service
            // in the middle of handling another job
            stopSelf(msg.arg1)

        }
    }

    override fun onCreate() {
        // Start up the thread running the service. Note that we create a separate thread because
        // the service normally runs in the process's main thread, which we don't want to block.
        // We also make it background priority so CPU-intensive work will not disrupt our UI
        HandlerThread("ServiceStartArgument", Process.THREAD_PRIORITY_BACKGROUND).apply {
            start()

            // Get the HandlerThread's Looper and use it for our Handler
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service Starting: $startId", Toast.LENGTH_SHORT).show()

        // For each start request, send a message to start a job and deliver the start ID
        // so we know which request we're stopping when we finish the job
        serviceHandler?.obtainMessage()?.also { message ->
            message.arg1 = startId
            serviceHandler?.sendMessage(message)
        }

        // If we get killed, after returning from here, restart
        return START_STICKY
    }

    private fun displayForegroundServiceNotification() {

    }

    // We don't provide binding to return null
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        Toast.makeText(this, "Service done", Toast.LENGTH_SHORT).show()
    }
}
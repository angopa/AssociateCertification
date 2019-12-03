package com.angopa.aad.codelabs.fundamentals.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.*

class BoundService : Service() {

    private val binder = LocalBinder()

    private val generator = Random()

    // Method for client
    val randomNumber: Int get() = generator.nextInt(100)

    /**
     * Class used for the client Binder. BEcause we know that this service always run in the
     * same process as its clients, we don't need to deal with IPC
     */
    inner class LocalBinder : Binder() {
        //Return this instance of LocalService so clients can call public methods
        fun getService(): BoundService = this@BoundService
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }
}
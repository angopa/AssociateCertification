package com.angopa.aad.codelabs.fundamentals.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import android.widget.Toast

/** Command to the service to display a message */
private const val MSG_SAY_HELLO = 1

class MessengerBoundService : Service() {
    /**
     * Target we publish for clients to send message to IncomingHandler
     */
    private lateinit var messenger: Messenger

    /**
     * When binding to the service, we return an interface to our messenger for sending
     * messages to the service
     */
    override fun onBind(intent: Intent?): IBinder? {
        Toast.makeText(applicationContext, "binding", Toast.LENGTH_SHORT).show()
        messenger = Messenger(IncomingHandler(this))
        return messenger.binder
    }

    /**
     * Handler for incoming messages from clients
     */
    internal class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_SAY_HELLO ->
                    Toast.makeText(applicationContext, "Hello World!", Toast.LENGTH_LONG).show()
                else -> super.handleMessage(msg)
            }
        }
    }
}
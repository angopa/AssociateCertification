package com.angopa.aad.androidcore.codelabs.nearbymessages

import android.annotation.SuppressLint
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityNearbyMessagesBinding
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.messages.Message
import com.google.android.gms.nearby.messages.MessageListener
import timber.log.Timber

/**
 *  Created by Andres Gonzalez on 02/20/2020.
 */
class NearbyMessagesActivity : BaseActivity() {
    private lateinit var binding: ActivityNearbyMessagesBinding

    private lateinit var mMessageListener: MessageListener
    private lateinit var mActiveMessage: Message

    private var text: String = ""

    override fun getScreenTitle(): Int {
        return R.string.nearby_messages_screen_title
    }

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityNearbyMessagesBinding>(
            this,
            R.layout.activity_nearby_messages
        ).apply {
            mMessageListener = object : MessageListener() {
                @SuppressLint("SetTextI18n")
                override fun onFound(p0: Message?) {
                    val message = p0?.content?.let { String(it) }
                    text += "\n $message"
                    logInfo.text = "Found Message $text"
                }

                @SuppressLint("SetTextI18n")
                override fun onLost(p0: Message?) {
                    val message = p0?.content?.let { String(it) }
                    text += "\n $message"
                    logInfo.text = "Lost sight of message $message"
                }
            }
        }

        publish("Hello World From Android")
    }

    private fun publish(message: String) {
        mActiveMessage = Message(message.toByteArray())
        Nearby.getMessagesClient(this).publish(mActiveMessage)
    }

    override fun onStart() {
        super.onStart()
        Nearby.getMessagesClient(this).subscribe(mMessageListener)
    }

    override fun onStop() {
        Nearby.getMessagesClient(this).unsubscribe(mMessageListener)
        super.onStop()
    }
}
package com.angopa.aad.codelabs.fundamentals.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.view.View
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityServiceBinding
import kotlinx.android.synthetic.main.include_toolbar.*
import timber.log.Timber

/** Command to the service to display a message */
private const val MSG_SAY_HELLO = 1

class ServiceActivity : BaseActivity() {
    private lateinit var binding: ActivityServiceBinding

    override fun getScreenTitle(): Int = R.string.service_screen_title

    /** Instance of BoundService use to access its methods */
    private lateinit var boundService: BoundService

    /** Flag indicating whether we are bind to the BoundService */
    private var bound: Boolean = false

    private lateinit var randomNumberButton: Button
    private lateinit var messengerButton: Button

    /** Messenger for communication with the MessengerBoundService */
    private var messenger: Messenger? = null

    /** Flag indicating whether we have called bind on the MessengerBoundService */
    private var messengerBound: Boolean = false

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityServiceBinding>(
            this, R.layout.activity_service
        ).apply {
            callback = object : Callback {
                override fun startSimpleService() = launchSimpleService()
                override fun startSimpleIntentService() = launchSimpleIntentService()
                @RequiresApi(Build.VERSION_CODES.O)
                override fun startForegroundService() = launchForegroundService()

                override fun startBoundService() = launchBoundService()
                override fun startMessengerBoundService() = launchMessengerBoundService()
            }

            randomNumberButton = randomNumberFromService.apply {
                text =
                    String.format(
                        getString(R.string.label_button_obtain_random_number),
                        "X"
                    )
                isEnabled = false
            }

            messengerButton = messengerBoundServiceButton
        }
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            unbindService(connection)
            bound = false
        }

        if (messengerBound) {
            unbindService(messengerBoundConnection)
            messengerBound = false
        }
    }

    private fun launchSimpleService() {
        Intent(this, ExampleService::class.java).also { intent ->
            startService(intent)
        }
    }

    private fun launchSimpleIntentService() {
        Intent(this, ExampleIntentService::class.java).also { intent ->
            startService(intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun launchForegroundService() {
        Intent(this, ForegroundService::class.java).also { intent ->
            startService(intent)
        }
    }

    private fun launchBoundService() {
        Intent(this, BoundService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun launchMessengerBoundService() {
        Intent(this, MessengerBoundService::class.java).also { intent ->
            bindService(intent, messengerBoundConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun updateRandomNumberButton() {
        randomNumberButton.isEnabled = bound
    }

    private fun updateMessengerButton() {
        messengerButton.isEnabled = messengerBound
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Timber.d("onServiceConnected()")
            // We bound to BoundService, cast the IBinder and get BoundService instance
            val binder = service as BoundService.LocalBinder
            boundService = binder.getService()
            bound = true
            updateRandomNumberButton()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Timber.d("onServiceDisconnected()")
            updateRandomNumberButton()
            bound = false
        }
    }

    /** Class for interacting with the main interface of the MessengerBoundService */
    private val messengerBoundConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            // This is called when the connection with the service has been established, giving
            // us the object we can use to interact with the service. We are communicating
            // with the service using a Messenger, so here we get a client-side representation
            // of that from the raw IBinder object.
            messenger = Messenger(service)
            messengerBound = true
            updateMessengerButton()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            messenger = null
            messengerBound = false
            updateMessengerButton()
        }
    }

    /**
     * Called when a button is clicked (the button in the layout file attaches to this method with
     * the android:onClick attribute
     */
    fun onButtonClick(view: View) {
        if (bound) {
            // Call a method from the LocalService
            // However, if this call were something that might hang, then this request should
            // occur in a separate thread to avoid slowing down the activity performance
            val num: Int = boundService.randomNumber
            randomNumberButton.text = String.format(
                getString(R.string.label_button_obtain_random_number),
                num
            )
        }
    }

    fun sayHello(view: View) {
        if (messengerBound) {
            // Create and send a message ti the service, using a supported 'what' value
            val msg: Message = Message.obtain(null, MSG_SAY_HELLO, 0, 0)
            try {
                messenger?.send(msg)
            } catch (e: RemoteException) {
                Timber.e(e)
            }
        }
    }

    interface Callback {
        fun startSimpleService()
        fun startSimpleIntentService()
        fun startForegroundService()
        fun startBoundService()
        fun startMessengerBoundService()
    }
}
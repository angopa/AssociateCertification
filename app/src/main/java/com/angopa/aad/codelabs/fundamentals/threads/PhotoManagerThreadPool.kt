package com.angopa.aad.codelabs.fundamentals.threads

import android.os.Handler
import android.os.Looper
import android.os.Message
import timber.log.Timber
import java.util.concurrent.BlockingDeque
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

//Creates a single instance of photo manager
object PhotoManagerThreadPool {

    // Set the amount of time and idle thread waits before terminating
    private const val KEEP_ALIVE_TIME = 1L

    // Set the Time Unit to seconds
    private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS

    // Gets the number of available cores (not always the same as the maximum number of cores)
    private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()

    // Instantiates the queue of Runnables as a LinkedBlockingQueue
    private val decodeWorkQueue: BlockingDeque<Runnable> = LinkedBlockingDeque<Runnable>()

    //Creates a Thread Pool Manager
    private val decodeThreadPool: ThreadPoolExecutor = ThreadPoolExecutor(
        NUMBER_OF_CORES,            // Initial pool size
        NUMBER_OF_CORES,            // Max Pool size
        KEEP_ALIVE_TIME,
        KEEP_ALIVE_TIME_UNIT,
        decodeWorkQueue
    )

    private val handler = object : Handler(Looper.getMainLooper()) {
        /**
         * handlerMessage() defines the operations to perform when the handler
         * receives a new Message to process
         */
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            Timber.d("Do something with the message")
        }
    }
}
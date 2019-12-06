package com.angopa.aad.codelabs.fundamentals.threads

import java.util.concurrent.BlockingDeque
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

//Creates a single instance
object CustomThreadPool {

    // Set the amount of time and idle thread waits before terminating
    private const val KEEP_ALIVE_TIME = 1L

    // Set the Time Unit to seconds
    private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS

    // Gets the number of available cores (not always the same as the maximum number of cores)
    private val NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors()

    // Instantiates the queue of Runnable as a LinkedBlockingQueue
    private var workQueue: BlockingDeque<Runnable> = LinkedBlockingDeque<Runnable>()

    //Creates a Thread Pool Manager
    private val workThreadPool: ThreadPoolExecutor = ThreadPoolExecutor(
        NUMBER_OF_CORES,            // Initial pool size
        NUMBER_OF_CORES,            // Max Pool size
        KEEP_ALIVE_TIME,
        KEEP_ALIVE_TIME_UNIT,
        workQueue
    )

    fun doSomething(runnable: SimpleRunnable) {
        workThreadPool.execute(runnable)
    }
}
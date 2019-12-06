package com.angopa.aad.codelabs.fundamentals.threads

import timber.log.Timber

class SimpleRunnable(
    private val className: String
) : Runnable {

    override fun run() {
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_BACKGROUND)
        Timber.d("Call Runnable from $className, here we go...")
        processCommand()
        Timber.d("Tell $className that I complete job! ")
    }

    private fun processCommand() {
        try {
            val duration = (Math.random() * 10000).toLong()
            Thread.sleep(duration)
        } catch (e: InterruptedException) {
            throw IllegalStateException(e)
        }
    }
}
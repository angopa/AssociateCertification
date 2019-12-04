package com.angopa.aad.codelabs.fundamentals.threads

import android.widget.TextView
import timber.log.Timber
import java.lang.IllegalStateException

class SimpleRunnable(private val sayHelloTextView: TextView) : Runnable {

    override fun run() {
        Timber.d("Start Running...")
        try {
            Thread.sleep(4000)
            sayHelloTextView.text = "Complete Job!"
        } catch (e: InterruptedException) {
            throw IllegalStateException(e)
        }

    }
}
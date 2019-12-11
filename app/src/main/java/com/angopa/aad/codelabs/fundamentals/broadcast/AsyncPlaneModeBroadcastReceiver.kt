package com.angopa.aad.codelabs.fundamentals.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import timber.log.Timber

class AsyncPlaneModeBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val pendingResult: PendingResult = goAsync()
        val asyncTask = Task(pendingResult, intent)
        asyncTask.execute()
    }

    private class Task(
        private val pendingResult: PendingResult,
        private val intent: Intent?
    ) : AsyncTask<String, Int, String>() {

        override fun doInBackground(vararg params: String?): String {
            val sb = StringBuilder()
            sb.append("Action: ${intent?.action}")
            sb.append("URI: ${intent?.toUri(Intent.URI_INTENT_SCHEME)}")
            return toString().also { Timber.d(sb.toString()) }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            // Must call finish() so the BroadcastReceiver can be recycled
            pendingResult.finish()
        }
    }
}
package com.angopa.aad.androidcore.codelabs.fundamentals.threads

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentThreadAsyncTaskBinding
import timber.log.Timber


class AsyncTaskFragment : Fragment() {
    private lateinit var binding: FragmentThreadAsyncTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentThreadAsyncTaskBinding>(
            inflater, R.layout.fragment_thread_async_task, container, false
        ).apply {
            navControlBackButton.setOnClickListener { view ->
                DownloadFileTask().cancel(true)
                view.findNavController().navigateUp()
            }

            launchAsyncTaskButton.setOnClickListener {
                DownloadFileTask().execute(1, 1, 1)
                DownloadFileTask().execute(1, 1, 1, 1, 1, 1, 1)
                DownloadFileTask().execute(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1)
                DownloadFileTask().execute(1, 1, 1, 1, 1, 1)
            }
            progressTextView.text = String.format(getString(R.string.label_progress), 0, 0)
        }

        return binding.root
    }

    fun showProgressStatus() {
        if (view == null) return
        binding.progressTextView.visibility = VISIBLE
    }

    fun setProgressPercent(percent: Int?, total: Int?) {
        if (view == null) return
        binding.progressTextView.text =
            String.format(getString(R.string.label_progress), percent.toString(), total.toString())
    }

    fun showDialog(message: String) {
        if (view == null) return
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    inner class DownloadFileTask : AsyncTask<Int, Int, Long>() {
        override fun onPreExecute() {
            showProgressStatus()
        }

        override fun doInBackground(vararg params: Int?): Long {
            val count = params.size
            Timber.d("Number of files: $count")
            for (i in 0..count) {
                Timber.d("Progress: %s", ((i / count.toFloat()) * 100).toInt())
                publishProgress(i, count)
                Thread.sleep(1000)
                if (isCancelled) break
            }

            return 16080
        }

        override fun onProgressUpdate(vararg values: Int?) {
            setProgressPercent(values[0], values[1])
        }

        override fun onPostExecute(result: Long?) {
            showDialog("Downloaded $result bytes")
        }

        override fun onCancelled() {
            showDialog("Download was canceled. ")
        }
    }
}
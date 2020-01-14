package com.angopa.aad.codelabs.fundamentals.workmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.angopa.aad.R
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.CoroutineDownloadWorker
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.DownloadWorker
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.LongRunningWorker
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.LongRunningWorker.Companion.KEY_INPUT_URL
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.LongRunningWorker.Companion.KEY_OUTPUT_FILE_NAME
import com.angopa.aad.databinding.FragmentThreadWorkManagerBinding

class ThreadWorkManagerFragment : Fragment() {
    private lateinit var binding: FragmentThreadWorkManagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentThreadWorkManagerBinding>(
            inflater,
            R.layout.fragment_thread_work_manager,
            container,
            false
        ).apply {
            clickHandler = object : OnClickHandlerListener {
                override fun threadingInWorker() = createWorker()
                override fun threadingCoroutineWorker() = createCoroutineWorker()
                override fun longRunningWorker() = createLongRunningWorker()
            }
        }

        return binding.root
    }

    private fun createWorker() {
        val request = OneTimeWorkRequestBuilder<DownloadWorker>()
            .build()

        WorkManager.getInstance(requireContext()).enqueue(request)
    }

    private fun createCoroutineWorker() {
        val request = OneTimeWorkRequestBuilder<CoroutineDownloadWorker>()
            .build()

        WorkManager.getInstance(requireContext()).enqueue(request)
    }

    private fun createLongRunningWorker() {
        val workData = workDataOf(KEY_INPUT_URL to "https://www.google.com", KEY_OUTPUT_FILE_NAME to "fileName" )

        val request = OneTimeWorkRequestBuilder<LongRunningWorker>()
            .setInputData(workData)
            .build()

        WorkManager.getInstance(requireContext()).enqueue(request)
    }

    interface OnClickHandlerListener {
        fun threadingInWorker()
        fun threadingCoroutineWorker()
        fun longRunningWorker()
    }
}
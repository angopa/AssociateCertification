package com.angopa.aad.codelabs.fundamentals.workmanager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.work.*
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.ProgressWorker
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.ProgressWorker.Companion.Progress
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.SimpleWork
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.SimpleWorkWithData
import com.angopa.aad.databinding.FragmentWorkManagerDasboardBinding
import timber.log.Timber
import java.util.concurrent.TimeUnit

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentWorkManagerDasboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentWorkManagerDasboardBinding>(
            inflater,
            R.layout.fragment_work_manager_dasboard,
            container,
            false
        ).apply {
            clickHandler = object : OnClickHandlerListener {
                override fun simpleWorkRequest() = createSimpleWorkRequest()
                override fun simpleConstraintWorkRequest() = createSimpleConstraintWorkRequest()
                override fun workRequestWithWorkData() = createWorkRequestWithWorkData()
                override fun workerObserved() = createSimpleObserverWorker()
                override fun intermediateWorkerProgress() = createIntermediateObserver()
                override fun chainingWorkers() = createChainOfWorkers()
                override fun simpleRecurringWork() = createSimpleRecurringWork()
                override fun threadInWorkManager() =
                    findNavController().navigate(DashboardFragmentDirections.actionDashboardToThreadInWorkManager())

                override fun blurOMaticCodelab() =
                    findNavController().navigate(DashboardFragmentDirections.actionDashboardToSelectImage())
            }
        }

        (activity as BaseActivity).resetTitle()

        return binding.root
    }

    private fun createSimpleWorkRequest() {
        val simpleWorkRequest = OneTimeWorkRequestBuilder<SimpleWork>()
            .setInitialDelay(DEFAULT_TIME_DELAY, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(requireContext()).enqueue(simpleWorkRequest)
    }

    private fun createSimpleConstraintWorkRequest() {
        //Create a Constraint object that defines when the task should run
        val constraints = Constraints.Builder()
            .setRequiresDeviceIdle(true)
            .setRequiresCharging(true)
            .build()

        //Then create a OneTimeWorkRequest that uses those constraints
        val simpleWorkRequest = OneTimeWorkRequestBuilder<SimpleWork>()
            .setInitialDelay(DEFAULT_TIME_DELAY, TimeUnit.SECONDS)
            .setConstraints(constraints)
            //Backoff Criteria cannot be set when .setRequiresDeviceIdle(true)
//            .setBackoffCriteria(
//                BackoffPolicy.LINEAR,
//                OneTimeWorkRequest.DEFAULT_BACKOFF_DELAY_MILLIS,
//                TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(requireContext()).enqueue(simpleWorkRequest)
    }

    private fun createWorkRequestWithWorkData() {
        //workDataOf (part of KTX) converts a list of parts to a [Data] object
        val workData = workDataOf(KEY_DATA_URI to "This is a test")

        val workRequest = OneTimeWorkRequestBuilder<SimpleWorkWithData>()
            .setInitialDelay(DEFAULT_TIME_DELAY, TimeUnit.SECONDS)
            .setInputData(workData)
            .build()

        WorkManager.getInstance(requireContext()).enqueue(workRequest)
    }

    private fun createSimpleObserverWorker() {
        val workData = workDataOf(KEY_DATA_URI to "This is a test")

        val workRequest = OneTimeWorkRequestBuilder<SimpleWorkWithData>()
            .setInputData(workData)
            .setInitialDelay(DEFAULT_TIME_DELAY, TimeUnit.SECONDS)
            .build()

        val workManager = WorkManager.getInstance(requireContext())
        workManager.enqueue(workRequest)
        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, Observer { workInfo ->
                if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
                    Toast.makeText(requireContext(), "Work finished", Toast.LENGTH_SHORT).show()
                    Timber.d("Response")
                }
            })
    }

    private fun createIntermediateObserver() {
        val workRequest = OneTimeWorkRequestBuilder<ProgressWorker>()
            .setInitialDelay(DEFAULT_TIME_DELAY, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(requireContext()).enqueue(workRequest)
        WorkManager.getInstance(requireContext())
            .getWorkInfoByIdLiveData(workRequest.id)
            .observe(this, Observer { workInfo ->
                if (workInfo != null) {
                    val progress = workInfo.progress
                    val value = progress.getInt(Progress, 0)
                    Timber.d("Progress $value")
                }
            })
    }

    private fun createChainOfWorkers() {
        val workRequest = OneTimeWorkRequestBuilder<SimpleWork>()
            .build()

        val workData = workDataOf(KEY_DATA_URI to "This is a test")
        val workRequest2 = OneTimeWorkRequestBuilder<SimpleWorkWithData>()
            .setInputData(workData)
            .build()

        val workRequest3 = OneTimeWorkRequestBuilder<ProgressWorker>()
            .build()

        WorkManager.getInstance(requireContext())
            .beginWith(workRequest3)
            .then(workRequest2)
            .then(workRequest)
            .enqueue()
    }

    private fun createSimpleRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicWorkRequest = PeriodicWorkRequestBuilder<SimpleWork>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(requireContext()).enqueue(periodicWorkRequest)
    }

    interface OnClickHandlerListener {
        fun simpleWorkRequest()
        fun simpleConstraintWorkRequest()
        fun workRequestWithWorkData()
        fun workerObserved()
        fun intermediateWorkerProgress()
        fun chainingWorkers()
        fun simpleRecurringWork()
        fun threadInWorkManager()
        fun blurOMaticCodelab()
    }

    companion object {
        const val DEFAULT_TIME_DELAY = 2L
        const val KEY_DATA_URI = "DashboardFragment.KEY_DATA_URI"
        const val KEY_IMAGE_RESULT = "DashboardFragment.KEY_IMAGE_RESULT"
    }
}
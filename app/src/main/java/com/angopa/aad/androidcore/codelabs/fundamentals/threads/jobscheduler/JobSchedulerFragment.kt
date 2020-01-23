package com.angopa.aad.androidcore.codelabs.fundamentals.threads.jobscheduler

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context.JOB_SCHEDULER_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentThreadJobSchedulerBinding

private const val JOB_ID = 0

class JobSchedulerFragment : Fragment() {

    private lateinit var binding: FragmentThreadJobSchedulerBinding

    private var jobScheduler: JobScheduler? = null

    private var seekBarInteger: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentThreadJobSchedulerBinding>(
            inflater, R.layout.fragment_thread_job_scheduler, container, false
        ).apply {
            callbackHandler = object : CallbackHandler {
                override fun scheduleJob() = launchScheduleJob()
                override fun cancelJob() = launchCancelJob()
            }

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    seekBarProgress.text = if (progress > 0) "$progress s" else "Not Set"
                    seekBarInteger = progress.toLong()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    // Method left empty intentionally
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    // Method left empty intentionally
                }
            })

            navControlBackButton.setOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        val intent = Intent(requireContext(), NotificationJobService::class.java)
        activity?.startService(intent)

        return binding.root
    }

    private fun launchScheduleJob() {
        val selectedNetWorkOption =
            when (binding.networkOptions.checkedRadioButtonId) {
                R.id.any_network -> JobInfo.NETWORK_TYPE_ANY
                R.id.wifi_network -> JobInfo.NETWORK_TYPE_UNMETERED
                else -> JobInfo.NETWORK_TYPE_NONE
            }

        jobScheduler = activity?.getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        val componentName = ComponentName(requireContext(), NotificationJobService::class.java)

        val jobInfo: JobInfo.Builder = JobInfo.Builder(JOB_ID, componentName)
        jobInfo.setRequiredNetworkType(selectedNetWorkOption)
        jobInfo.setMinimumLatency(1 * 1000) // wait at least
        jobInfo.setRequiresDeviceIdle(binding.idleSwitch.isChecked)
        jobInfo.setRequiresCharging(binding.chargingSwitch.isChecked)

        if (seekBarInteger > 0) {
            jobInfo.setOverrideDeadline(seekBarInteger * 1000) // maximum delay
        }

        jobScheduler?.schedule(jobInfo.build())

        showToastMessage(getString(R.string.toast_message_job_schdeuler_started))
    }

    private fun launchCancelJob() {
        if (jobScheduler != null) {
            jobScheduler?.cancelAll()
            jobScheduler = null
            showToastMessage(getString(R.string.toast_message_job_scheduler_cancelled))
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    interface CallbackHandler {
        fun scheduleJob()
        fun cancelJob()
    }
}
package com.angopa.aad.androidcore.codelabs.fundamentals.threads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentThreadScheduledPoolBinding
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

class ScheduledThreadPoolFragment : Fragment() {
    private lateinit var binding: FragmentThreadScheduledPoolBinding

    private val maxThreadSize = Runtime.getRuntime().availableProcessors()

    private lateinit var executor: ScheduledExecutorService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentThreadScheduledPoolBinding>(
            inflater, R.layout.fragment_thread_scheduled_pool, container, false
        ).apply {
            navControlBackButton.setOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        executor = Executors.newScheduledThreadPool(maxThreadSize)
        val runnable = SimpleRunnable(this.javaClass.simpleName)
        executor.scheduleWithFixedDelay(runnable, 2, 2, TimeUnit.SECONDS)

        return binding.root
    }

    override fun onDestroy() {
        executor.shutdown()
        super.onDestroy()
    }
}
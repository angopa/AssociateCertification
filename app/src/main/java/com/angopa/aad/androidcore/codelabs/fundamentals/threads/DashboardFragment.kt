package com.angopa.aad.androidcore.codelabs.fundamentals.threads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentThreadDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentThreadDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentThreadDashboardBinding>(
            inflater, R.layout.fragment_thread_dashboard, container, false
        ).apply {
            launchSimpleRunnableButton.setOnClickListener { view ->
                val direction = DashboardFragmentDirections.actionDashboardToSimpleThread()
                view.findNavController().navigate(direction)
            }

            launchRecurrentRunnableButton.setOnClickListener { view ->
                val direction = DashboardFragmentDirections.actionDashboardToRecurrentThread()
                view.findNavController().navigate(direction)
            }

            launchThreadPoolButton.setOnClickListener { view ->
                val direction = DashboardFragmentDirections.actionDashboardToThreadPool()
                view.findNavController().navigate(direction)
            }

            launchAsyncTaskButton.setOnClickListener { view ->
                val direction = DashboardFragmentDirections.actionDashboardToAsyncTask()
                view.findNavController().navigate(direction)
            }

            launchLoaderButton.setOnClickListener {view ->
                val direction = DashboardFragmentDirections.actionDashboardToLoader()
                view.findNavController().navigate(direction)
            }

            launchJobSchedulerButton.setOnClickListener { view ->
                val direction = DashboardFragmentDirections.actionDashboardToJobScheduler()
                view.findNavController().navigate(direction)
            }
        }

        return binding.root
    }

}
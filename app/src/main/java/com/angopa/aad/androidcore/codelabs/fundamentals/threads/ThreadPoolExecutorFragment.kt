package com.angopa.aad.androidcore.codelabs.fundamentals.threads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentThreadPoolExecutorBinding

class ThreadPoolExecutorFragment : Fragment() {
    private lateinit var binding: FragmentThreadPoolExecutorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentThreadPoolExecutorBinding>(
            inflater, R.layout.fragment_thread_pool_executor, container, false
        ).apply {
            launchFixedThreadPoolButton.setOnClickListener { view ->
                val direction =
                    ThreadPoolExecutorFragmentDirections.actionThreadPoolExecutorToFixedThreadPool()
                view.findNavController().navigate(direction)
            }

            launchCachedThreadPoolButton.setOnClickListener { view ->
                val direction =
                    ThreadPoolExecutorFragmentDirections.actionThreadPoolExecutorToCachedThreadPool()
                view.findNavController().navigate(direction)
            }

            launchCustomThreadPoolButton.setOnClickListener { view ->
                val direction =
                    ThreadPoolExecutorFragmentDirections.actionThreadPoolExecutorToCustomThreadPool()
                view.findNavController().navigate(direction)
            }

            launchProgressBarExample.setOnClickListener { view ->
                val direction =
                    ThreadPoolExecutorFragmentDirections.actionThreadPoolExecutorToProgressBarExample()
                view.findNavController().navigate(direction)
            }

            navControlBackButton.setOnClickListener { view ->
                view.findNavController().navigateUp()
            }

        }

        return binding.root
    }
}
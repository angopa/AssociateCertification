package com.angopa.aad.codelabs.fundamentals.threads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentThreadDashboardBinding

class DashboardThreadFragment : Fragment() {

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
                val direction = DashboardThreadFragmentDirections.actionDashboardToSimpleThread()
                view.findNavController().navigate(direction)
            }
        }

        return binding.root
    }

}
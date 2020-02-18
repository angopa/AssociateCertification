package com.angopa.aad.androidui.codelabs.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentNavigationDashboardBinding

/**
 *  Created by Andres Gonzalez on 02/17/2020.
 */
class NavigationDashboardFragment : Fragment() {
    private lateinit var binding: FragmentNavigationDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentNavigationDashboardBinding>(
            inflater,
            R.layout.fragment_navigation_dashboard,
            container,
            false
        ).apply{
            basicSampleButton.setOnClickListener {
                val directions = NavigationDashboardFragmentDirections.actionDashboardToBasicSample()
                findNavController().navigate(directions)
            }
        }
        return binding.root
    }
}
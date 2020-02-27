package com.angopa.aad.androidui.codelabs.transition.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentTransitionDashboardBinding

/**
 *  Created by Andres Gonzalez on 02/27/2020.
 */
class TransitionDashboardFragment : Fragment() {
    private lateinit var binding: FragmentTransitionDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentTransitionDashboardBinding>(
            inflater,
            R.layout.fragment_transition_dashboard,
            container,
            false
        ).apply {
            transitionBetweenFragments.setOnClickListener {
                val directions =
                    TransitionDashboardFragmentDirections.actionDashboardToTransitionBetweenFragments()
                findNavController().navigate(directions)
            }
        }
        return binding.root
    }
}
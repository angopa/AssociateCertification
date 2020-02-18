package com.angopa.aad.androidui.codelabs.navigation.basicsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentNavigationMatchBinding

/**
 *  Created by Andres Gonzalez on 02/17/2020.
 */
class MatchFragment : Fragment() {
    private lateinit var binding: FragmentNavigationMatchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentNavigationMatchBinding>(
            inflater,
            R.layout.fragment_navigation_match,
            container,
            false
        ).apply {
            playBtn3.setOnClickListener {
                val directions = MatchFragmentDirections.actionMatchToInGame()
                findNavController().navigate(directions)
            }
        }
        return binding.root
    }
}
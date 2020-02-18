package com.angopa.aad.androidui.codelabs.navigation.basicsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentNavigationTitleScreenBinding

/**
 *  Created by Andres Gonzalez on 02/17/2020.
 */
class TitleScreenFragment : Fragment() {
    private lateinit var binding: FragmentNavigationTitleScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentNavigationTitleScreenBinding>(
            inflater,
            R.layout.fragment_navigation_title_screen,
            container,
            false
        ).apply {
            playButton.setOnClickListener {
                val directions = TitleScreenFragmentDirections.actionTitleScreenToRegister()
                findNavController().navigate(directions)
            }

            leaderboardButton.setOnClickListener {
                val directions = TitleScreenFragmentDirections.actionTitleScreenToLeaderboard()
                findNavController().navigate(directions)
            }
        }
        return binding.root
    }
}
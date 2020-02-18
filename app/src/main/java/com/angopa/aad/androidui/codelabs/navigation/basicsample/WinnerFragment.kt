package com.angopa.aad.androidui.codelabs.navigation.basicsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentNavigationWinnerBinding

/**
 *  Created by Andres Gonzalez on 02/18/2020.
 */
class WinnerFragment : Fragment() {
    private lateinit var binding: FragmentNavigationWinnerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate<FragmentNavigationWinnerBinding>(
                inflater,
                R.layout.fragment_navigation_winner,
                container,
                false
            ).apply {
                playBtn2.setOnClickListener {
                    val directions = WinnerFragmentDirections.actionWinnerToMatch()
                    findNavController().navigate(directions)
                }

                leaderboardBtn2.setOnClickListener {
                    val directions = WinnerFragmentDirections.actionWinnerToLeaderboard()
                    findNavController().navigate(directions)
                }
            }
        return binding.root
    }
}
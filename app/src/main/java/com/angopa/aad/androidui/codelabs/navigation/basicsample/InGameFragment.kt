package com.angopa.aad.androidui.codelabs.navigation.basicsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentNavigationInGameBinding

/**
 *  Created by Andres Gonzalez on 02/17/2020.
 */
class InGameFragment : Fragment() {
    private lateinit var binding: FragmentNavigationInGameBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentNavigationInGameBinding>(
            inflater,
            R.layout.fragment_navigation_in_game,
            container,
            false
        ).apply {
            checkbox.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_in_game_to_game_over)
            }
            checkbox2.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_in_game_to_game_over)
            }
            checkbox3.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.action_in_game_to_game_over)
            }
            checkbox4.setOnClickListener {
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_in_game_to_result_winner)
            }
        }

        return binding.root
    }
}
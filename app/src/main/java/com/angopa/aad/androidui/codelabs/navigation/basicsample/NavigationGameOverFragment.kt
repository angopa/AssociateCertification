package com.angopa.aad.androidui.codelabs.navigation.basicsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentNavigationGameOverBinding

/**
 *  Created by Andres Gonzalez on 02/18/2020.
 */
class NavigationGameOverFragment : Fragment() {
    private lateinit var binding: FragmentNavigationGameOverBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentNavigationGameOverBinding>(
            inflater,
            R.layout.fragment_navigation_game_over,
            container,
            false
        ).apply {
            playBtn.setOnClickListener {
                val directions = NavigationGameOverFragmentDirections.actionGameOverToMatch()
                findNavController().navigate(directions)
            }
        }
        return binding.root
    }

}
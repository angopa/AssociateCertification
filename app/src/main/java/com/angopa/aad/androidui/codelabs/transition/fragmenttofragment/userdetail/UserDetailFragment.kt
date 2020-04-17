package com.angopa.aad.androidui.codelabs.transition.fragmenttofragment.userdetail

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentTransitionUserDetailBinding

/**
 *  Created by Andres Gonzalez on 02/27/2020.
 */
class UserDetailFragment : Fragment() {
    private lateinit var binding: FragmentTransitionUserDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentTransitionUserDetailBinding>(
            inflater,
            R.layout.fragment_transition_user_detail,
            container,
            false
        ).apply {
            val args = arguments
            if (args != null) {
                val transitionName = args.getString("transitionName")
                profileImage.transitionName = transitionName
            }
        }

        sharedElementEnterTransition = TransitionInflater
            .from(requireContext())
            .inflateTransition(R.transition.image_shared_element_transition)

        enterTransition = TransitionInflater
            .from(requireContext())
            .inflateTransition(android.R.transition.no_transition)

        return binding.root
    }
}
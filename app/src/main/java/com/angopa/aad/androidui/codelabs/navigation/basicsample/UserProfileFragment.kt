package com.angopa.aad.androidui.codelabs.navigation.basicsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentNavigationUserProfileBinding

/**
 *  Created by Andres Gonzalez on 02/17/2020.
 */
class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentNavigationUserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentNavigationUserProfileBinding>(
            inflater,
            R.layout.fragment_navigation_user_profile,
            container,
            false
        ).apply {
            val name = arguments?.getString("userName") ?: "Ali Connors"
            profileUserName.text = name
        }
        return binding.root
    }
}
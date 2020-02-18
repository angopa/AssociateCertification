package com.angopa.aad.androidui.codelabs.navigation.basicsample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentNavigationRegisterBinding

/**
 *  Created by Andres Gonzalez on 02/17/2020.
 */
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentNavigationRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentNavigationRegisterBinding>(
            inflater,
            R.layout.fragment_navigation_register,
            container,
            false
        ).apply {
            signupButton.setOnClickListener {
                val directions = RegisterFragmentDirections.actionRegisterToMatch()
                findNavController().navigate(directions)
            }
        }

        return binding.root
    }

}
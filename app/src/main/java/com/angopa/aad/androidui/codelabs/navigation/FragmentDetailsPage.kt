package com.angopa.aad.androidui.codelabs.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentDetailsPageBinding

/**
 *  Created by Andres Gonzalez on 02/17/2020.
 */
class FragmentDetailsPage : Fragment() {
    private lateinit var binding: FragmentDetailsPageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details_page, container, false)
        return binding.root
    }
}
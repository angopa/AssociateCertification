package com.angopa.aad.androidui.codelabs.motion.simpleexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.angopa.aad.R

/**
 *  Created by Andres Gonzalez on 02/10/2020.
 */
class SimpleExampleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_motion_01_basic, container, false)
    }
}
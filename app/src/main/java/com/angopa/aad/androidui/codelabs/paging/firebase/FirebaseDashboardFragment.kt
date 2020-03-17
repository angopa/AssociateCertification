package com.angopa.aad.androidui.codelabs.paging.firebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentFirebaseDashboardBinding

/**
 *  Created by Andres Gonzalez on 03/17/2020.
 */
class FirebaseDashboardFragment : Fragment() {
    private lateinit var binding: FragmentFirebaseDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentFirebaseDashboardBinding>(
            inflater,
            R.layout.fragment_firebase_dashboard,
            container,
            false
        ).apply {
            firebasePagingBtn.setOnClickListener {
                val directions =
                    FirebaseDashboardFragmentDirections.actionDashboardToPagingExample()
                findNavController().navigate(directions)
            }
        }
        return binding.root
    }
}
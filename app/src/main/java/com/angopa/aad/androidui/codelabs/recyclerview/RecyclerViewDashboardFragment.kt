package com.angopa.aad.androidui.codelabs.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentRecyclerViewDashboardBinding

/**
 *  Created by Andres Gonzalez on 02/03/2020.
 */
class RecyclerViewDashboardFragment : Fragment() {
    private lateinit var binding: FragmentRecyclerViewDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentRecyclerViewDashboardBinding>(
            inflater,
            R.layout.fragment_recycler_view_dashboard,
            container,
            false
        ).apply {
            lifecycleOwner = viewLifecycleOwner

            clickHandler = object : ClickHandler {
                override fun simpleRecyclerView() {
                    val direction =
                        RecyclerViewDashboardFragmentDirections.actionDashboardToSimpleRecycler()
                    findNavController().navigate(direction)
                }

                override fun recyclerViewAnimations() {
                    val direction =
                        RecyclerViewDashboardFragmentDirections.actionDashboardToRecyclerAnimations()
                    findNavController().navigate(direction)
                }
            }
        }

        return binding.root
    }


    interface ClickHandler {
        fun simpleRecyclerView()
        fun recyclerViewAnimations()
    }
}

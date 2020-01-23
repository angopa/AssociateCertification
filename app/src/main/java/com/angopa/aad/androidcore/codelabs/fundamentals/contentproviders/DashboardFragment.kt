package com.angopa.aad.androidcore.codelabs.fundamentals.contentproviders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentProvidersDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentProvidersDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentProvidersDashboardBinding>(
            inflater, R.layout.fragment_providers_dashboard, container, false
        ).apply {
            retrievingDataButton.setOnClickListener { view ->
                val direction = DashboardFragmentDirections.dashboardToUserDictionary()
                view.findNavController().navigate(direction)
            }

        }

        return binding.root
    }

}
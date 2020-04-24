package com.angopa.aad.androiddata

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.angopa.aad.R
import com.angopa.aad.adapters.LinkAdapter
import com.angopa.aad.androiddata.codelabs.roomlivedataviewmodel.RoomCodelabActivity
import com.angopa.aad.databinding.FragmentAndroidDataBinding
import com.angopa.aad.utilities.InjectorUtils

class AndroidDataManagementFragment : Fragment() {

    private lateinit var binding: FragmentAndroidDataBinding

    private val androidDataManagementViewModel: AndroidDataManagementViewModel by viewModels {
        InjectorUtils.provideAndroidDataManagementViewModelFactory(requireContext(), "android_data")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAndroidDataBinding>(
            inflater,
            R.layout.fragment_android_data, container, false
        ).apply {
            viewModel = androidDataManagementViewModel
            lifecycleOwner = viewLifecycleOwner

            val adapter = LinkAdapter()
            linksRecyclerView.adapter = adapter

            callback = object : Callback {
                override fun roomLiveDataViewModel() {
                    launchActivity(RoomCodelabActivity::class.java)
                }
            }

            subscribeUi(adapter)
        }

        return binding.root
    }

    private fun <T : Activity> launchActivity(activity: Class<T>) {
        startActivity(Intent(requireContext(), activity))
    }

    private fun subscribeUi(adapter: LinkAdapter) {
        androidDataManagementViewModel.links.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
    }

}

interface Callback {
    fun roomLiveDataViewModel()
}
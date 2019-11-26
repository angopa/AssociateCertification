package com.angopa.aad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.angopa.aad.adapters.LinkAdapter
import com.angopa.aad.databinding.FragmentAndroidDebuggingBinding
import com.angopa.aad.utilities.InjectorUtils
import com.angopa.aad.viewmodel.AndroidDebuggingViewModel

class AndroidDebugging : Fragment() {

    private lateinit var binding: FragmentAndroidDebuggingBinding

    private val androidDebuggingViewModel: AndroidDebuggingViewModel by viewModels {
        InjectorUtils.provideAndroidDebuggingViewModelFactory(requireContext(), "android_debug")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAndroidDebuggingBinding>(
            inflater, R.layout.fragment_android_debugging, container, false
        ).apply {
            viewModel = androidDebuggingViewModel
            lifecycleOwner = viewLifecycleOwner

            val adapter = LinkAdapter()
            linksRecyclerView.adapter = adapter

            subscribeUi(adapter)
        }

        return binding.root
    }

    private fun subscribeUi(adapter: LinkAdapter) {
            androidDebuggingViewModel.links.observe(viewLifecycleOwner) { result ->
                adapter.submitList(result)
            }
    }
}
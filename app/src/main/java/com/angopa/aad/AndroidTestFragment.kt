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
import com.angopa.aad.databinding.FragmentAndroidTestBinding
import com.angopa.aad.utilities.InjectorUtils
import com.angopa.aad.viewmodel.AndroidTestViewModel

class AndroidTestFragment : Fragment() {
    private lateinit var binding: FragmentAndroidTestBinding

    private val androidTestViewModel: AndroidTestViewModel by viewModels {
        InjectorUtils.provideAndroidTestViewModelFactory(requireContext(), "android_test")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAndroidTestBinding>(
            inflater, R.layout.fragment_android_test, container, false
        ).apply {
            viewModel = androidTestViewModel
            lifecycleOwner = viewLifecycleOwner

            val adapter = LinkAdapter()
            linksRecyclerView.adapter = adapter

            subscribeUi(adapter)
        }

        return binding.root
    }

    private fun subscribeUi(adapter: LinkAdapter) {
        androidTestViewModel.links.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
    }
}
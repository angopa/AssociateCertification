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
import com.angopa.aad.databinding.FragmentAndroidUiBinding
import com.angopa.aad.utilities.InjectorUtils
import com.angopa.aad.viewmodel.AndroidUiViewModel

class AndroidUiFragment : Fragment() {

    private lateinit var binding: FragmentAndroidUiBinding

    private val androidUiViewModel: AndroidUiViewModel by viewModels {
        InjectorUtils.provideAndroidUiViewModelFactory(requireContext(), "android_ui")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAndroidUiBinding>(
            inflater, R.layout.fragment_android_ui, container, false
        ).apply {
            viewModel = androidUiViewModel
            lifecycleOwner = viewLifecycleOwner

            val adapter = LinkAdapter()
            linkList.adapter = adapter

            subscribeUi(adapter)
        }

        return binding.root
    }

    private fun subscribeUi(adapter: LinkAdapter) {
        androidUiViewModel.links.observe(viewLifecycleOwner) {result ->
            adapter.submitList(result)
        }
    }

}
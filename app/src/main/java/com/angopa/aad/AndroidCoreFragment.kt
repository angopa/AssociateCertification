package com.angopa.aad

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.angopa.aad.adapters.LinkAdapter
import com.angopa.aad.codelabs.localization.LocalizationActivity
import com.angopa.aad.codelabs.snackbar.SnackbarActivity
import com.angopa.aad.codelabs.toast.ToastActivity
import com.angopa.aad.databinding.FragmentAndroidCoreBinding
import com.angopa.aad.utilities.InjectorUtils
import com.angopa.aad.viewmodel.AndroidCoreViewModel

class AndroidCoreFragment : Fragment() {

    private lateinit var binding: FragmentAndroidCoreBinding

    private val androidCoreViewModel: AndroidCoreViewModel by viewModels {
        InjectorUtils.provideAndroidCoreViewModelFactory(requireContext(), "android_core")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAndroidCoreBinding>(
            inflater, R.layout.fragment_android_core, container, false
        ).apply {
            viewModel = androidCoreViewModel
            lifecycleOwner = viewLifecycleOwner

            val adapter = LinkAdapter()
            linkList.adapter = adapter

            subscribeUi(adapter)

            callback = object : Callback {
                override fun toastContainerTapped() {
                    startActivity(Intent(requireContext(), ToastActivity::class.java))
                }

                override fun snackbarContainerTapped() {
                    startActivity(Intent(requireContext(), SnackbarActivity::class.java))
                }

                override fun localizationContainerTapped() {
                    startActivity(Intent(requireContext(), LocalizationActivity::class.java))
                }
            }
        }

        return binding.root
    }

    private fun subscribeUi(adapter: LinkAdapter) {
        androidCoreViewModel.links.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result)
        }
    }

    interface Callback {
        fun toastContainerTapped()
        fun snackbarContainerTapped()
        fun localizationContainerTapped()
    }
}
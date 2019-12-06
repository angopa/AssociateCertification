package com.angopa.aad.codelabs.fundamentals.threads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentThreadCustomPoolBinding

class CustomThreadPoolFragment : Fragment() {
    private lateinit var binding: FragmentThreadCustomPoolBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentThreadCustomPoolBinding>(
            inflater, R.layout.fragment_thread_custom_pool, container, false
        ).apply {
            navControlBackButton.setOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        CustomThreadPool.doSomething(SimpleRunnable(this.javaClass.simpleName))

        return binding.root
    }
}
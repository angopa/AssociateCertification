package com.angopa.aad.androidcore.codelabs.fundamentals.threads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentSimpleRunnableBinding

class SimpleRunnableFragment : Fragment() {
    private lateinit var binding: FragmentSimpleRunnableBinding

    private lateinit var runnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentSimpleRunnableBinding>(
            inflater, R.layout.fragment_simple_runnable, container, false
        ).apply {
            launchRunnableButton.setOnClickListener {
                Thread(runnable).start()
            }

            navControlBackButton.setOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        runnable = SimpleRunnable(this.javaClass.simpleName)

        return binding.root
    }
}
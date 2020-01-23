package com.angopa.aad.androidcore.codelabs.fundamentals.threads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentThreadFixedPoolBinding
import java.util.concurrent.Executors

class FixedThreadPoolFragment : Fragment() {
    private lateinit var binding: FragmentThreadFixedPoolBinding

    private val maxThreadSize = Runtime.getRuntime().availableProcessors()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentThreadFixedPoolBinding>(
            inflater, R.layout.fragment_thread_fixed_pool, container, false
        ).apply {
            navControlBackButton.setOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        val executor = Executors.newFixedThreadPool(maxThreadSize)
        for (x in 0 until 10) {
            val runnable = SimpleRunnable(this.javaClass.simpleName + "_" + x)
            executor.execute(runnable)
        }

        executor.shutdown()

        return binding.root
    }
}
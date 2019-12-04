package com.angopa.aad.codelabs.fundamentals.threads

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentSimpleRunnableBinding

class SimpleRunnableFragment : Fragment() {
    private lateinit var binding: FragmentSimpleRunnableBinding

    private var handler = Handler()

    private lateinit var runnable: Runnable

    private var index = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_simple_runnable, container, false)

        runnable = Runnable {
            binding.sayHelloTextView.text =
                String.format(getString(R.string.label_thread_hello_message) + "_%s", index++)
            handler.postDelayed(runnable, 1000)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        handler.postDelayed(runnable, 1000)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(runnable)
    }
}
package com.angopa.aad.androidcore.codelabs.fundamentals.threads

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentRecurrentRunnableBinding

class RecurrentRunnableFragment : Fragment() {
    private lateinit var binding: FragmentRecurrentRunnableBinding

    private var handler = Handler()

    private lateinit var runnable: Runnable

    private var index = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_recurrent_runnable, container, false)

        runnable = Runnable {
            binding.sayHelloTextView.text =
                String.format(getString(R.string.label_thread_hello_message) + "_%s", index++)
            handler.postDelayed(runnable, 1000)
        }

        binding.navControlBackButton.setOnClickListener { view ->
            view.findNavController().navigateUp()
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
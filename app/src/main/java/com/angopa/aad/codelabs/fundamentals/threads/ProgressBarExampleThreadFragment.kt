package com.angopa.aad.codelabs.fundamentals.threads

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentThreadProgressBarBinding
import timber.log.Timber

const val START_PROCESS = 100
const val UPDATE_COUNT = 101
const val MAX_PROGRESS_BAR = 100

class ProgressBarExampleThreadFragment : Fragment() {
    private lateinit var binding: FragmentThreadProgressBarBinding

    private lateinit var thread: Thread

    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentThreadProgressBarBinding>(
            inflater, R.layout.fragment_thread_progress_bar, container, false
        ).apply {
            navControlBackButton.setOnClickListener { view ->
                view.findNavController().navigateUp()
            }
            progressBar.max = MAX_PROGRESS_BAR

            counterTextView.text = String.format(getString(R.string.label_thread_counter), "0")

            startThreadButton.setOnClickListener {
                handler.sendEmptyMessage(START_PROCESS)
            }
        }

        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                when (msg.what) {
                    START_PROCESS -> thread.start()
                    UPDATE_COUNT -> {
                        if (view == null) return
                        binding.counterTextView.text =
                            String.format(
                                getString(R.string.label_thread_counter),
                                msg.arg1
                            )
                    }
                }
            }
        }

        thread = Thread(Runnable {
            for (x in 1..100) {
                binding.progressBar.progress = x
                try {
                    Thread.sleep(500)
                } catch (e: InterruptedException) {
                    Timber.e(e)
                    Thread.currentThread().interrupt()
                }
                val msg = Message()
                msg.what = UPDATE_COUNT
                msg.arg1 = x
                handler.sendMessage(msg)
            }

        })

        return binding.root
    }

}
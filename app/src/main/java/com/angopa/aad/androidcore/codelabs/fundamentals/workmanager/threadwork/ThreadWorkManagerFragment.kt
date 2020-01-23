package com.angopa.aad.androidcore.codelabs.fundamentals.workmanager.threadwork

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentThreadWorkManagerBinding

class ThreadWorkManagerFragment : Fragment() {
    private lateinit var binding: FragmentThreadWorkManagerBinding

    private val viewModel: ThreadWorkViewModel by viewModels {
        InjectorUtils.getThreadWorkViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentThreadWorkManagerBinding>(
            inflater,
            R.layout.fragment_thread_work_manager,
            container,
            false
        ).apply {
            clickHandler = object : OnClickHandlerListener {
                override fun threadingInWorker() = createWorker()
                override fun threadingCoroutineWorker() = createCoroutineWorker()
                override fun longRunningWorker() = createLongRunningWorker()
            }
        }

        return binding.root
    }

    private fun createWorker() {
        viewModel.createWorker()
    }

    private fun createCoroutineWorker() {
        viewModel.createCoroutineWorker()
    }

    private fun createLongRunningWorker() {
        viewModel.createLongRunningWorker()
    }

    interface OnClickHandlerListener {
        fun threadingInWorker()
        fun threadingCoroutineWorker()
        fun longRunningWorker()
    }
}
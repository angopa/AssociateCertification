package com.angopa.aad.dependencyinjection

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentDaggerBinding
import com.angopa.aad.dependencyinjection.daggercodelab.login.LoginActivity

class DaggerFragment : Fragment() {
    private lateinit var binding: FragmentDaggerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dagger, container, false)

        binding.daggerSimpleExample.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    com.angopa.aad.dependencyinjection.daggerbasic.LoginActivity::class.java
                )
            )
        }

        binding.daggerCodelab.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
        }

        return binding.root
    }
}
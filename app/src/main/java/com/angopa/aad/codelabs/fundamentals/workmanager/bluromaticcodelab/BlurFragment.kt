package com.angopa.aad.codelabs.fundamentals.workmanager.bluromaticcodelab

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentBlurBinding
import com.bumptech.glide.Glide

class BlurFragment : Fragment() {
    private lateinit var binding: FragmentBlurBinding
    private lateinit var viewModel: BlurViewModel
    private val args: BlurFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(BlurViewModel::class.java)

        binding = DataBindingUtil.inflate<FragmentBlurBinding>(
            inflater,
            R.layout.fragment_blur,
            container,
            false
        ).apply {
            viewModel.setImageUri(args.imageUri)

            if (viewModel.getImageUri() != null) {
                Glide.with(requireContext())
                    .load(viewModel.getImageUri())
                    .into(imageView)
            }

            goButton.setOnClickListener {
                viewModel.applyBlur(1)
            }
        }

        (activity as BaseActivity).updateTitle(getString(R.string.blur_codelab_title))

        return binding.root
    }

}
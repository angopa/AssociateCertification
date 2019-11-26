package com.angopa.aad

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.angopa.aad.databinding.FragmentDetailBinding
import timber.log.Timber


class DetailFragment : Fragment() {

    private val args : DetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentDetailBinding>(
            inflater, R.layout.fragment_detail, container, false
        ).apply {
            Timber.d("LinkUri: %s", args.linkUri)
            webView.loadUrl(args.linkUri)

            toolbar.setNavigationOnClickListener {view ->
                view.findNavController().navigateUp()
            }
        }

        return binding.root
    }

}
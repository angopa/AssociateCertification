package com.angopa.aad.codelabs.fundamentals.workmanager.bluromaticcodelab

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentBlurBinding
import com.angopa.aad.utilities.KEY_IMAGE_URI
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
                viewModel.applyBlur(getBlurLevel(radioBlurGroup.checkedRadioButtonId))
            }

            seeFileButton.setOnClickListener {
                val currentUri = viewModel.getBlurOutputUri()
                if (currentUri != null) {
                    val intent = Intent(Intent.ACTION_VIEW, currentUri)
                    if (intent.resolveActivity(activity?.packageManager!!) != null) {
                        startActivity(intent)
                    }
                }
            }

            cancelButton.setOnClickListener { viewModel.cancelWork() }

            viewModel.getOutputWorkInfo().observe(this@BlurFragment, Observer { listOfWorkInfos ->
                // If the is no matching work info, do nothing
                if (listOfWorkInfos == null || listOfWorkInfos.isEmpty()) {
                    return@Observer
                }

                // We only care about the first status.
                // Every continuation has only one worker tagged TAG_OUTPUT
                val workInfo = listOfWorkInfos[0]

                if (!workInfo.state.isFinished) {
                    showWorkInProgress()
                } else {
                    showWorkFinished()

                    val outputData = workInfo.outputData
                    val outputImageUri = outputData.getString(KEY_IMAGE_URI)

                    // If there is an output file show "See File" button
                    if (!TextUtils.isEmpty(outputImageUri.toString())) {
                        viewModel.setBlurOutputUri(outputImageUri.toString())
                        seeFileButton.visibility = View.VISIBLE
                    }
                }
            })
        }

        (activity as BaseActivity).updateTitle(getString(R.string.blur_codelab_title))

        return binding.root
    }

    private fun getBlurLevel(checkedRadioButtonId: Int): Int {
        return when (checkedRadioButtonId) {
            R.id.radio_blur_lv_1 -> 0
            R.id.radio_blur_lv_2 -> 1
            R.id.radio_blur_lv_3 -> 2
            else -> 0
        }
    }

    private fun showWorkInProgress() {
        binding.progressBar.visibility = View.VISIBLE
        binding.cancelButton.visibility = View.VISIBLE
        binding.goButton.visibility = View.GONE
        binding.seeFileButton.visibility = View.GONE

    }

    private fun showWorkFinished() {
        binding.progressBar.visibility = View.GONE
        binding.cancelButton.visibility = View.GONE
        binding.goButton.visibility = View.VISIBLE
    }
}
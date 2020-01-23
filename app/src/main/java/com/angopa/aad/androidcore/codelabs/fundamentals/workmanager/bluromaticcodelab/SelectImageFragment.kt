package com.angopa.aad.androidcore.codelabs.fundamentals.workmanager.bluromaticcodelab

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentSelectImageBinding
import timber.log.Timber

class SelectImageFragment : Fragment() {
    private lateinit var binding: FragmentSelectImageBinding

    companion object {
        private const val REQUEST_CODE_IMAGE = 100
        private const val REQUEST_CODE_PERMISSIONS = 101
        private const val KEY_PERMISSIONS_REQUEST_COUNT = "KEY_PERMISSIONS_REQUEST_COUNT"
        private const val MAX_NUMBER_REQUEST_PERMISSIONS = 2

        private val permissions = listOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    private var permissionRequestCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentSelectImageBinding>(
            inflater,
            R.layout.fragment_select_image,
            container,
            false
        ).apply {
            // Create request to get image from filesystem when button clicked
            selectImage.setOnClickListener {
                val chooseIntent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(chooseIntent, REQUEST_CODE_IMAGE)
            }
        }

        (activity as BaseActivity).updateTitle(getString(R.string.blur_codelab_title))

        if (savedInstanceState != null) {
            permissionRequestCount = savedInstanceState.getInt(
                KEY_PERMISSIONS_REQUEST_COUNT,
                0
            )
        }

        // Make sure the app has correct permissions to run
        requestPermissionsIfNecessary()

        return binding.root
    }

    /**
     * Request permissions twice - if the user denies twice then show a toast about how to update
     * the permission for storage. Also disable the button if we don't have access to pictures on
     * the device.
     */
    private fun requestPermissionsIfNecessary() {
        if (!checkAllPermissions()) {
            if (permissionRequestCount < MAX_NUMBER_REQUEST_PERMISSIONS) {
                permissionRequestCount++
                ActivityCompat.requestPermissions(
                    activity as BaseActivity,
                    permissions.toTypedArray(),
                    REQUEST_CODE_PERMISSIONS
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.set_permissions_in_settings),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /** Permission Checking **/
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            requestPermissionsIfNecessary()
        }
    }

    private fun checkAllPermissions(): Boolean {
        var hasPermissions = true
        for (permission in permissions) {
            hasPermissions =
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    permission
                ) == PackageManager.PERMISSION_GRANTED
        }
        return hasPermissions
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQUEST_CODE_IMAGE -> handleImageRequestResult(data)
                    else -> Timber.d("Unknown request code.")
                }
            }
            else -> Timber.d("Unexpected Result code $resultCode")
        }
    }

    private fun handleImageRequestResult(data: Intent?) {
        val imageUri = when {
            data?.clipData != null -> data.clipData?.getItemAt(0)?.uri
            data?.data != null -> data.data
            else -> null
        }

        if (imageUri == null) {
            Timber.e("Invalid input image Uri.")
            return
        }

        val directions = SelectImageFragmentDirections.actionDashboardToBlurCodelab(imageUri.toString())
        findNavController().navigate(directions)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_PERMISSIONS_REQUEST_COUNT, permissionRequestCount)
    }
}
package com.angopa.aad

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.angopa.aad.databinding.ActivityMainBinding
import com.angopa.aad.utilities.AppConfiguration
import com.angopa.aad.utilities.DialogFactory
import com.angopa.aad.utilities.PermissionUtil
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        validatePermissionsRequired()

    }

    private fun validatePermissionsRequired() {
        tryGettingLocationPermission()
    }

    private fun tryGettingLocationPermission() {
        Timber.d(getLocationPermissionState().toString())
        if (getLocationPermissionState() != PermissionUtil.PermissionState.GRANTED) {
            if (getLocationPermissionState() == PermissionUtil.PermissionState.DENIED_PERMANENTLY ||
                getLocationPermissionState() == PermissionUtil.PermissionState.DENIED_TEMPORARILY
            ) {
                DialogFactory(AppConfiguration.get()).createInformationalDialog(
                    this,
                    "Provide an explanation about why do it need this permission.",
                    "Permission Required"
                ).show()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(ACCESS_FINE_LOCATION),
                    PERMISSIONS_REQUEST_LOCATION
                )
            }
        }
    }

    private fun getLocationPermissionState(): PermissionUtil.PermissionState =
        PermissionUtil.getPermissionState(this, ACCESS_FINE_LOCATION)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val PERMISSIONS_REQUEST_LOCATION = 123
    }
}

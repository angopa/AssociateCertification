package com.angopa.aad.utilities

import android.app.Activity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED

class PermissionUtil private constructor() {

    enum class PermissionState {
        GRANTED, DENIED_TEMPORARILY, DENIED_PERMANENTLY
    }

    companion object {
        fun getPermissionState(
            activity: Activity,
            permissionName: String
        ): PermissionState {
            return if (ContextCompat.checkSelfPermission(
                    activity,
                    permissionName
                ) != PERMISSION_GRANTED
            ) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        permissionName
                    )
                ) {
                    PermissionState.DENIED_PERMANENTLY
                }
                PermissionState.DENIED_TEMPORARILY
            } else {
                PermissionState.GRANTED
            }
        }
    }

}
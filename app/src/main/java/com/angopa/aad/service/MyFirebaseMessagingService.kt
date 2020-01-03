package com.angopa.aad.service

import com.google.firebase.messaging.FirebaseMessagingService
import timber.log.Timber

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Timber.d("Refresh Token: %s", token)
    }
}
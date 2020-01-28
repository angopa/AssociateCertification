package com.angopa.aad.dependencyinjection.daggercodelab.settings

import androidx.lifecycle.ViewModel
import com.angopa.aad.dependencyinjection.daggercodelab.user.UserDataRepository
import com.angopa.aad.dependencyinjection.daggercodelab.user.UserManager
import javax.inject.Inject

/**
 * SettingsViewModel is the ViewModel that [SettingsActivity] uses to handle complex logic.
 */
class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
    private val userManager: UserManager
) : ViewModel() {

    fun refreshNotifications() {
        userDataRepository.refreshUnreadNotifications()
    }

    fun logout() {
        userManager.logout()
    }
}
package com.angopa.aad.dependencyinjection.daggercodelab.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angopa.aad.dependencyinjection.daggerbasic.ActivityScope
import com.angopa.aad.dependencyinjection.daggercodelab.registration.enterdetails.EnterDetailError
import com.angopa.aad.dependencyinjection.daggercodelab.registration.enterdetails.EnterDetailSuccess
import com.angopa.aad.dependencyinjection.daggercodelab.registration.enterdetails.EnterDetailsViewState
import com.angopa.aad.dependencyinjection.daggercodelab.user.UserManager
import javax.inject.Inject

private const val MAX_LENGTH = 5

/**
 * RegistrationViewModel is the ViewModel that the Registration flow ([RegistrationActivity]
 * and fragments) uses to keep user's input data.
 *
 * @Inject tells Dagger how to provide instances of this type. Dagger also knows
 * that UserManager is a dependency.
 */
@ActivityScope
class RegistrationViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {

    private val _enterDetailState = MutableLiveData<EnterDetailsViewState>()
    val enterDetailState: LiveData<EnterDetailsViewState>
        get() = _enterDetailState

    private var username: String? = null
    private var password: String? = null
    private var acceptedTCs: Boolean? = null

    fun updateUserData(userName: String, password: String) {
        this.username = userName
        this.password = password
    }

    fun acceptTCs() {
        acceptedTCs = true
    }

    fun registerUser() {
        assert(username != null)
        assert(password != null)
        assert(acceptedTCs == true)
        userManager.regiterUser(username!!, password!!)
    }

    fun validateInput(userName: String, password: String) {
        when {
            userName.length < MAX_LENGTH -> _enterDetailState.value =
                EnterDetailError("Username has to be longer that 4 characters")
            password.length < MAX_LENGTH -> _enterDetailState.value =
                EnterDetailError("Password has to be longer that 4 characters")
            else -> _enterDetailState.value = EnterDetailSuccess
        }
    }
}
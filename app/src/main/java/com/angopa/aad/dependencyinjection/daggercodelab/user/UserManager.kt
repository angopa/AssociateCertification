package com.angopa.aad.dependencyinjection.daggercodelab.user

import com.angopa.aad.dependencyinjection.daggercodelab.storage.Storage
import javax.inject.Inject
import javax.inject.Singleton

private const val REGISTERED_USER = "registered_user"
private const val PASSWORD_SUFFIX = "password"

/**
 * Handles User lifecycle. Manages registrations, logs in and logs out.
 * Knows when the user is logged in.
 *
 * Marked with @Singleton since we only one an instance of UserManager in the application graph.
 */
@Singleton
class UserManager @Inject constructor(
    private val storage: Storage,
    // Since UserManager will be in charge of managing the UserComponent lifecycle,
    // it needs to know how to create instances of it
    private val userComponentFactory: UserComponent.Factory
) {

    /**
     *  UserComponent is specific to a logged in user. Holds an instance of UserComponent.
     *  This determines if the user is logged in or not, when the user logs in,
     *  a new Component will be created. When the user logs out, this will be null.
     */
    var userComponent: UserComponent? = null
        private set

    val userName: String
        get() = storage.getString(REGISTERED_USER)

    fun isUserLoggedIn() = userComponent != null

    fun isUserRegistered() = storage.getString(REGISTERED_USER).isNotEmpty()

    fun regiterUser(userName: String, password: String) {
        storage.setString(REGISTERED_USER, userName)
        storage.setString("$userName$PASSWORD_SUFFIX", password)
        userJustLoggedIn()
    }

    fun loginUser(userName: String, password: String): Boolean {
        val registeredUser = this.userName
        if (registeredUser != userName) return false

        val registeredPassword = storage.getString("$userName$PASSWORD_SUFFIX")
        if (registeredPassword != password) return false

        userJustLoggedIn()
        return true
    }

    fun logout() {
        // When the user logs out, we remove the instance of UserComponent from memory
        userComponent = null
    }

    fun unregister() {
        val userName = storage.getString(REGISTERED_USER)
        storage.setString(REGISTERED_USER, "")
        storage.setString("$userName$PASSWORD_SUFFIX", "")
        logout()
    }

    private fun userJustLoggedIn() {
        // When the user logs in, we create a new instance of UserComponent
        userComponent = userComponentFactory.create()
    }
}
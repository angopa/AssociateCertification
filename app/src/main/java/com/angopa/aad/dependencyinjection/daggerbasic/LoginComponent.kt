package com.angopa.aad.dependencyinjection.daggerbasic

import dagger.Subcomponent

/**
 * @Subcomponent annotation informs Dagger this interface is a Dagger Subcomponent
 * Classes annotated with @ActivityScope are scoped to the graph and the same instance
 * of that type is provided every time the type is requested
 */
@ActivityScope
@Subcomponent
interface LoginComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

    // This tells Dagger that LoginActivity request injection from LoginComponent
    // so that this subcomponent graph needs to satisfy all the dependencies of the
    // fields that LoginActivity is injecting
    fun inject(activity: LoginActivity)
    fun inject(usernameFragment: LoginUsernameFragment)
    fun inject(passwordFragment: LoginPasswordFragment)
}
package com.angopa.aad.dependencyinjection.daggercodelab.registration

import com.angopa.aad.dependencyinjection.daggerbasic.ActivityScope
import com.angopa.aad.dependencyinjection.daggercodelab.registration.enterdetails.EnterDetailsFragment
import com.angopa.aad.dependencyinjection.daggercodelab.registration.termsandconditions.TermsAndConditionsFragment
import dagger.Subcomponent

// Scope annotation that the RegistrationComponent uses
// Classes annotated with @ActivityScope will have a unique instance in this Component
@ActivityScope
// Definition of a Dagger subcomponent
@Subcomponent
interface RegistrationComponent {

    // Factory to create instances of RegistrationComponent
    @Subcomponent.Factory
    interface Factory {
        fun create() : RegistrationComponent
    }

    // Classes that can be injected by this Component
    fun inject(activity: RegistrationActivity)
    fun inject(fragment: EnterDetailsFragment)
    fun inject(fragment: TermsAndConditionsFragment)
}
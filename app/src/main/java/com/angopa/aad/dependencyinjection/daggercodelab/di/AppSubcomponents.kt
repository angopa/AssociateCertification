package com.angopa.aad.dependencyinjection.daggercodelab.di

import com.angopa.aad.dependencyinjection.daggercodelab.login.LoginComponent
import com.angopa.aad.dependencyinjection.daggercodelab.registration.RegistrationComponent
import com.angopa.aad.dependencyinjection.daggercodelab.user.UserComponent
import dagger.Module

// This module tells a Component which are its subcomponents
@Module(
    subcomponents = [
        RegistrationComponent::class,
        LoginComponent::class,
        UserComponent::class
    ]
)
class AppSubcomponents
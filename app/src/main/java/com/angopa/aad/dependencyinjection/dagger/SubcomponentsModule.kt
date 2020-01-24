package com.angopa.aad.dependencyinjection.dagger

import dagger.Module

/**
 * The Subcomponents attribute in the @Module annotation tells Dagger what
 * Subcomponents are children of the Component this module is included in.
 */
@Module(subcomponents = [LoginComponent::class])
class SubcomponentsModule {}
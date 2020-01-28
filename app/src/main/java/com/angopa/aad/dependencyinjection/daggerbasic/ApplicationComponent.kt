package com.angopa.aad.dependencyinjection.daggerbasic

import dagger.Component
import javax.inject.Singleton

/**
 * Dagger Component:
 * @Component makes Dagger create a graph of dependencies
 * @Scope annotations on a @Component interface informs Dagger that classes annotated with this
 * annotation (i.e. @Singleton) are bound to the life time graph and so the same instance of
 * that type is provided every time the type is required
 *
 */
@Singleton
@Component(modules = [NetworkModule::class, SubcomponentsModule::class])
interface ApplicationComponent {
    // This function exposes the LoginComponent Factory out ot the graph so consumers
    // can use it to obtain new instances of LoginComponent
    fun loginComponent(): LoginComponent.Factory

    fun userRepository(): UserRepository
}
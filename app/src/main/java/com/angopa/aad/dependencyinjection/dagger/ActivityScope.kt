package com.angopa.aad.dependencyinjection.dagger

import javax.inject.Scope

/**
 * Defining of a custom scope called ActivityScope
 */
@Scope
@MustBeDocumented
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ActivityScope
package com.angopa.aad.dependencyinjection.manualdependencyinjection

interface Factory {
    fun <T : LoginViewModel> create(): T
}
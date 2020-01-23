package com.angopa.aad.manualdependencyinjection

interface Factory {
    fun <T : LoginViewModel> create(): T
}
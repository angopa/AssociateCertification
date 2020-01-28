package com.angopa.aad.dependencyinjection.daggercodelab.storage

interface Storage {
    fun setString(key: String, value: String)
    fun getString(key: String): String

}
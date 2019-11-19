package com.angopa.aad

import java.util.*

/**
 * Interface to be use by the [AAD] for storing a data
 */
interface IStorage {
    fun getLocale(): Locale
    fun persistLocale(locale: Locale)
}
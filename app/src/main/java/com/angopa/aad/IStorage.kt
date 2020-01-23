package com.angopa.aad

import com.angopa.aad.androidcore.codelabs.fundamentals.activity.ActivityModel
import java.util.*

/**
 * Interface to be use by the [AAD] for storing a data
 */
interface IStorage {
    fun getLocale(): Locale
    fun persistLocale(locale: Locale)
    fun getMultipleOptions(): ActivityModel
    fun persistMultipleOptions(activityModel: ActivityModel)
}
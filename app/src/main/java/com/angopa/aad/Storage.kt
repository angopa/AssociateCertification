package com.angopa.aad

import android.content.Context
import com.angopa.aad.androidcore.codelabs.fundamentals.activity.ActivityModel
import org.json.JSONObject
import java.util.*

/**
 * Default implementation of [Storage] using [SharedPreferences].
 */
class Storage private constructor(
    context: Context,
    private val defaultLocale: Locale = Locale.getDefault(),
    private val defaultActivityModel: ActivityModel = ActivityModel("", ""),
    preferenceName: String = DEFAULT_PREFERENCE_NAME
) : IStorage {

    private val prefs = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)

    override fun getLocale(): Locale {
        // there is no predefined way to serialize/deserialize Locale object
        return if (!prefs.getString(LANGUAGE_KEY, null).isNullOrBlank()) {
            val json = JSONObject(prefs.getString(LANGUAGE_KEY, null)!!)
            val language = json.getString(LANGUAGE_JSON_KEY)
            val country = json.getString(COUNTRY_JSON_KEY)
            val variant = json.getString(VARIANT_JSON_KEY)
            Locale(language, country, variant)
        } else {
            defaultLocale
        }
    }

    override fun persistLocale(locale: Locale) {
        val json = JSONObject().apply {
            put(LANGUAGE_JSON_KEY, locale.language)
            put(COUNTRY_JSON_KEY, locale.country)
            put(VARIANT_JSON_KEY, locale.variant)
        }

        prefs.edit().putString(LANGUAGE_KEY, json.toString()).apply()
    }

    override fun getMultipleOptions(): ActivityModel {
        return if (!prefs.getString(MULTIPLE_OPTIONS_KEY, null).isNullOrBlank()) {
            val json = JSONObject(prefs.getString(MULTIPLE_OPTIONS_KEY, null)!!)
            val contactUri = json.getString(CONTACT_URI_KEY).replace("\\/", "/")
            val imageUri = json.getString(IMAGE_URI_KEY).replace("\\/", "/")
            ActivityModel(contactUri, imageUri)
        } else {
            defaultActivityModel
        }
    }

    override fun persistMultipleOptions(
        activityModel: ActivityModel
    ) {
        val json = JSONObject().apply {
            put(CONTACT_URI_KEY, activityModel.contactUri)
            put(IMAGE_URI_KEY, activityModel.imageUri)
        }
        prefs.edit().putString(MULTIPLE_OPTIONS_KEY, json.toString()).apply()
    }

    companion object {
        private const val DEFAULT_PREFERENCE_NAME = "aad_preference"
        private const val LANGUAGE_KEY = "language_key"
        private const val LANGUAGE_JSON_KEY = "language"
        private const val COUNTRY_JSON_KEY = "country"
        private const val VARIANT_JSON_KEY = "variant"
        private const val CONTACT_URI_KEY = "contact_uri"
        private const val IMAGE_URI_KEY = "image_uri"
        private const val MULTIPLE_OPTIONS_KEY = "view_mode"

        @Volatile
        private var instance: Storage? = null

        fun getInstance(context: Context) = instance ?: synchronized(this) {
            instance ?: Storage(context).also { instance = it }
        }
    }
}



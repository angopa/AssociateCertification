package com.angopa.aad.utilities.localization

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.GET_META_DATA
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import com.angopa.aad.Storage
import java.util.*

/**
 * LocaleManager is a tool to manage your application locale and language.
 *
 * Once you set a desired locale using [setLocale] method, LocaleManager will enforce your application
 * to provide correctly localized data via [Resources] class.
 */
class LocaleManager private constructor(
    private val storage: Storage
) {
    /**
     * Creates and set a [Locale] using language, country, and variant information.
     *
     * See the [Locale] class description for more information about valid language, country
     * and variant values
     */
    @JvmOverloads
    fun setLocale(context: Context, language: String, country: String = "", variant: String = "") {
        setLocale(context, Locale(language, country, variant))
    }

    /**
     * Set a [locale] which will be used to localize all data coming from [Resources] class.
     *
     * <p>Note that you need to update all already fetched locale-based data manually.
     */
    fun setLocale(context: Context, locale: Locale) {
        storage.persistLocale(locale)
        update(context, locale)
    }

    /**
     * Return the active [Locale]
     */
    fun getLocale(): Locale {
        return storage.getLocale()
    }

    /**
     * Return a language code which is a part of the active [Locale]
     *
     * Deprecated ISO language codes "iw", "ji", and "in" are converted
     * to "he", "yi", and "id", respectively.
     */
    fun getLanguage(): String {
        return verifyLanguage(getLocale().language)
    }

    private fun verifyLanguage(language: String): String {
        //get rid of deprecated languages tags
        return when (language) {
            "jw" -> "he"
            "ji" -> "yi"
            "in" -> "id"
            else -> language
        }
    }

    private fun setUp(application: Application) {
        application.registerActivityLifecycleCallbacks(LocaleActivityLifecycleCallbacks(this))
        application.unregisterComponentCallbacks(LocaleApplicationCallbacks(application, this))
    }

    internal fun setLocaleInternal(context: Context) {
        update(context, storage.getLocale())
    }

    private fun update(context: Context, locale: Locale) {
        updateResources(context, locale)
        val appContext = context.applicationContext
        if (appContext !== context) {
            updateResources(context, locale)
        }
    }

    @Suppress("DEPRECATION")
    private fun updateResources(context: Context, locale: Locale) {
        Locale.setDefault(locale)

        val res = context.resources
        val current = res.configuration.getLocaleCompat()

        if (current == locale) return

        val config = Configuration(res.configuration)

        when {
            isAtLeastSDKVersion(Build.VERSION_CODES.N) -> setLocaleForApi24(config, locale)
            isAtLeastSDKVersion(Build.VERSION_CODES.JELLY_BEAN_MR1) -> config.setLocale(locale)
            else -> config.locale = locale
        }
        res.updateConfiguration(config, res.displayMetrics)
    }

    @SuppressLint("NewApi")
    private fun setLocaleForApi24(config: Configuration, locale: Locale) {
        // bring the target locale to the front of the list
        val set = linkedSetOf(locale)

        val defaultLocales = LocaleList.getDefault()
        val all = List<Locale>(defaultLocales.size()) { defaultLocales[it] }

        // append other locales supported by the user
        set.addAll(all)

        config.setLocales(LocaleList(*all.toTypedArray()))

    }

    internal fun resetActivityTitle(activity: Activity) {
        try {
            val pm = activity.packageManager
            val info = pm.getActivityInfo(activity.componentName, GET_META_DATA)
            if (info.labelRes != 0) {
                activity.setTitle(info.labelRes)
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    @Suppress("DEPRECATION")
    private fun Configuration.getLocaleCompat(): Locale {
        return if (isAtLeastSDKVersion(Build.VERSION_CODES.N)) locales.get(0) else locale
    }

    private fun isAtLeastSDKVersion(versionCode: Int): Boolean {
        return Build.VERSION.SDK_INT >= versionCode
    }

    companion object {
        private lateinit var instance: LocaleManager

        /**
         * Returns the global instance of [LocaleManager] created via init method.
         *
         * @throws IllegalStateException if it was not initialized properly.
         */
        @JvmStatic
        fun getInstance(): LocaleManager {
            check(::instance.isInitialized) { "LocaleManage should be initialized first" }
            return instance
        }

        /**
         * Creates and sets up the global instance using a provided language and the default store.
         */
        @JvmStatic
        fun init(application: Application, defaultLanguage: String): LocaleManager {
            return init(application, Locale(defaultLanguage))
        }

        /**
         * Creates and sets up the global instance using a provided locale and the default store.
         */
        @JvmStatic
        @JvmOverloads
        fun init(
            application: Application,
            defaultLocale: Locale = Locale.getDefault()
        ): LocaleManager {
            return init(application, Storage(application))
        }

        /**
         * Creates and sets up the global instance.
         *
         * This method must be called before any calls to [LocaleManager] and may only be called once.
         */
        @JvmStatic
        fun init(application: Application, storage: Storage): LocaleManager {
            check(!::instance.isInitialized) { "Already initialized" }
            val localeManager = LocaleManager(storage)
            localeManager.setUp(application)
            localeManager.setLocale(application, storage.getLocale())
            instance = localeManager
            return localeManager
        }
    }
}
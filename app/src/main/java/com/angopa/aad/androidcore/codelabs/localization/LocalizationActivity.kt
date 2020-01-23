package com.angopa.aad.androidcore.codelabs.localization

import android.content.Intent
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.MainActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityLocalizationBinding
import com.angopa.aad.utilities.LANGUAGE_ENGLISH
import com.angopa.aad.utilities.LANGUAGE_SPANISH
import java.text.DateFormat
import java.util.*

class LocalizationActivity : BaseActivity() {

    private lateinit var binding: ActivityLocalizationBinding

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityLocalizationBinding>(
            this@LocalizationActivity, R.layout.activity_localization
        ).apply {
            //Display Default Locale Configuration
            defaultLocation.text = String.format(
                getString(R.string.label_locale_language),
                LocaleManager.getInstance().getLanguage()
            )

            //Display Date format base in local Configuration
            val date = Date()
            val dateFormat =
                DateFormat.getDateInstance(DateFormat.LONG, LocaleManager.getInstance().getLocale())
            dateTextView.text =
                String.format(getString(R.string.label_locale_date), dateFormat.format(date))

            en.setOnClickListener { setNewLocale(LANGUAGE_ENGLISH) }
            es.setOnClickListener { setNewLocale(LANGUAGE_SPANISH) }
        }
    }

    private fun setNewLocale(language: String) {
        LocaleManager.getInstance().setLocale(this, language)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))

        Toast.makeText(this, getString(R.string.activity_restarted_message), Toast.LENGTH_SHORT)
            .show()
    }

    override fun getScreenTitle(): Int = R.string.localization_screen_title
}
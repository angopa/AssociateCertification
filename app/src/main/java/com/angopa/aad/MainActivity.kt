package com.angopa.aad

import android.content.res.Configuration
import android.os.Bundle
import android.util.Config
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.angopa.aad.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val languageToLoad = "es"
        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale

        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

    }
}

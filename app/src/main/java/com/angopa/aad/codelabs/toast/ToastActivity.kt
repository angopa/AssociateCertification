package com.angopa.aad.codelabs.toast

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityToastBinding
import kotlinx.android.synthetic.main.activity_toast.*

/**
 * Example class for Toast implementation base on:
 * https://developer.android.com/guide/topics/ui/notifiers/toasts#kotlin
 *
 * Implement binding approach to handle click events on the view.
 */
class ToastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityToastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityToastBinding>(
            this, R.layout.activity_toast
        ).apply {
            callback = object : Callback {
                override fun normalToast() {
                    Toast.makeText(this@ToastActivity, getString(R.string.toast_simple_message), Toast.LENGTH_SHORT).show()
                }

                override fun positioningToast() {
                    val toast = Toast.makeText(this@ToastActivity, getString(R.string.toast_over_here_message), Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.TOP, 0, 0)
                    toast.show()
                }

                override fun customToast() {
                    val inflater = layoutInflater
                    val layout: View = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))
                    val text: TextView = layout.findViewById(R.id.text)
                    text.text = getString(R.string.toast_custom_message)
                    with(Toast(applicationContext)) {
                        setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                        duration = Toast.LENGTH_LONG
                        view = layout
                        show()
                    }
                }
            }
        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(R.string.label_toast_screen_title)
    }

    interface Callback {
        fun normalToast()

        fun positioningToast()

        fun customToast()
    }
}
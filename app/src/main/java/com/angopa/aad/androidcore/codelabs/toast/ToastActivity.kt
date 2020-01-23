package com.angopa.aad.androidcore.codelabs.toast

import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.androidcore.codelabs.toast.viewmodel.ToastViewModel
import com.angopa.aad.databinding.ActivityToastBinding

/**
 * Example class for Toast implementation base on:
 * https://developer.android.com/guide/topics/ui/notifiers/toasts#kotlin
 *
 * Implement binding approach to handle click events on the view.
 */
class ToastActivity : BaseActivity() {

    private lateinit var binding: ActivityToastBinding

    private val toastViewModel: ToastViewModel by viewModels {
        InjectorUtil.provideToastActivityViewModelFactory()
    }

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityToastBinding>(
            this, R.layout.activity_toast
        ).apply {
            callback = object : Callback {
                override fun normalToast() {
                    Toast.makeText(
                        this@ToastActivity,
                        getString(R.string.toast_simple_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun positioningToast() {
                    val toast = Toast.makeText(
                        this@ToastActivity,
                        getString(R.string.toast_over_here_message),
                        Toast.LENGTH_SHORT
                    )
                    toast.setGravity(Gravity.TOP, 0, 0)
                    toast.show()
                }

                override fun customToast() {
                    val inflater = layoutInflater
                    val layout: View = inflater.inflate(
                        R.layout.custom_toast,
                        findViewById(R.id.custom_toast_container)
                    )
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
    }

    override fun getScreenTitle(): Int = R.string.label_toast_screen_title

    interface Callback {
        fun normalToast()
        fun positioningToast()
        fun customToast()
    }
}
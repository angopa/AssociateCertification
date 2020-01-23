package com.angopa.aad.androidcore.codelabs.snackbar

import android.graphics.Color.RED
import android.graphics.Color.YELLOW
import android.view.Gravity
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivitySnackbarBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Example class for Snackbar providing different examples of it use.
 *
 * Implement binding approach to handle click events on the view.
 */
class SnackbarActivity : BaseActivity() {

    private lateinit var binding: ActivitySnackbarBinding

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivitySnackbarBinding>(
            this@SnackbarActivity, R.layout.activity_snackbar
        ).apply {
            callback = object : Callback {
                override fun onNormalSnackBarClick() {
                    Snackbar.make(
                        root,
                        getString(R.string.snack_bar_simple_message),
                        Snackbar.LENGTH_INDEFINITE
                    ).show()
                }

                override fun onActionCallClick() {
                    Snackbar.make(
                        root,
                        getString(R.string.snack_bar_delete_dummy),
                        Snackbar.LENGTH_SHORT
                    )
                        .setAction(getString(R.string.generic_label_undo)) {
                            Snackbar.make(
                                root,
                                getString(R.string.snack_bar_restore_dummy),
                                Snackbar.LENGTH_SHORT
                            )
                                .show()
                        }
                        .show()
                }

                override fun onCustomViewClick() {
                    Snackbar.make(
                        root,
                        getString(R.string.snack_bar_custom_message),
                        Snackbar.LENGTH_SHORT
                    )
                        .setAction(getString(R.string.generic_label_reset)) {
                            val toast = Toast.makeText(
                                this@SnackbarActivity,
                                getString(R.string.generic_do_something_message),
                                Toast.LENGTH_LONG
                            )
                            toast.setGravity(Gravity.CENTER, 0, 0)
                            toast.show()
                        }.setTextColor(YELLOW)
                        .setActionTextColor(RED)
                        .show()
                }
            }
        }
    }

    override fun getScreenTitle(): Int = R.string.label_snack_bar_screen_title

    interface Callback {
        fun onNormalSnackBarClick()
        fun onActionCallClick()
        fun onCustomViewClick()
    }
}
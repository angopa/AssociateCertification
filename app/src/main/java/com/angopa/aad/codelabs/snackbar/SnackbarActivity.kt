package com.angopa.aad.codelabs.snackbar

import android.graphics.Color.RED
import android.graphics.Color.YELLOW
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivitySnackbarBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_snackbar.*

/**
 * Example class for Snackbar providing different examples of it use.
 *
 * Implement binding approach to handle click events on the view.
 */
class SnackbarActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySnackbarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySnackbarBinding>(
            this@SnackbarActivity, R.layout.activity_snackbar
        ).apply {
            callback = object : Callback {
                override fun onNormalSnackBarClick() {
                    Snackbar.make(root, "Default Snackbar", Snackbar.LENGTH_INDEFINITE).show()
                }

                override fun onActionCallClick() {
                    Snackbar.make(root, "Message is Deleted", Snackbar.LENGTH_SHORT)
                        .setAction("UNDO") {
                            Snackbar.make(root, "Message is restored", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                        .show()
                }

                override fun onCustomViewClick() {
                    Snackbar.make(root, "Custom Snackbar", Snackbar.LENGTH_SHORT)
                        .setAction("RESET") {
                            val toast = Toast.makeText(
                                this@SnackbarActivity,
                                "Do something",
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

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setTitle(R.string.label_snack_bar_screen_title)
    }

    interface Callback {
        fun onNormalSnackBarClick()
        fun onActionCallClick()
        fun onCustomViewClick()
    }
}
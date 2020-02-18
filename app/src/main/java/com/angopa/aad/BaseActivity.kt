package com.angopa.aad

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.include_toolbar.*

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getBindingComponent()

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(getScreenTitle())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    abstract fun getScreenTitle(): Int

    abstract fun getBindingComponent()

    fun showAlertDialog(dialog: AlertDialog) {
        dialog.show()
    }

    fun <T: String> displayToastMessage(message: T) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun updateTitle(title: String) {
        supportActionBar?.title = title
    }

    fun resetTitle() {
        supportActionBar?.setTitle(getScreenTitle())
    }
}
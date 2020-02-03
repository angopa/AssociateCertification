package com.angopa.aad.androidui.codelabs.constraintlayout

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 *  Created by Andres Gonzalez on 2020-01-29.
 */
class ConstraintLayoutActivity : AppCompatActivity() {
    private var tag: String = "activity_constraint_layout"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(tag)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        setContentView(tag)
    }

    override fun onBackPressed() {
        if (tag != "activity_constraint_layout") {
            setContentView("activity_constraint_layout")
        } else {
            super.onBackPressed()
        }
    }

    fun show(view: View) {
        tag = view.tag.toString()
        setContentView(tag)
    }

    fun showConstraintSetExample(view: View) {
        startActivity(Intent(this, ConstraintSetActivity::class.java))
    }

    private fun setContentView(tag: String) {
        val id: Int = resources.getIdentifier(tag, "layout", packageName)
        setContentView(id)
    }
}

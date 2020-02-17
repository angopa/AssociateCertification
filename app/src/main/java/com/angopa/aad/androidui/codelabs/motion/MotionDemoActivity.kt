package com.angopa.aad.androidui.codelabs.motion

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import com.angopa.aad.R

/**
 *  Created by Andres Gonzalez on 02/10/2020.
 */
class MotionDemoActivity : AppCompatActivity() {

    private lateinit var container: View

    companion object {
        fun getInstance(context: Context, layoutFileId: Int): Intent =
            Intent(context, MotionDemoActivity::class.java).apply {
                putExtra("layout_field_id", layoutFileId)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = intent.getIntExtra("layout_field_id", R.layout.fragment_motion_01_basic)
        setContentView(layout)

        if (layout == R.layout.fragment_motion_11_coordinatorlayout) {
            val icon = findViewById<ImageView>(R.id.icon)
            icon?.clipToOutline = true
        }
    }

    fun changeStage(view: View?) {
        val motionLayout = container as? MotionLayout ?: return
        if (motionLayout.progress > 0.5f) {
            motionLayout.transitionToStart()
        } else {
            motionLayout.transitionToEnd()
        }
    }
}
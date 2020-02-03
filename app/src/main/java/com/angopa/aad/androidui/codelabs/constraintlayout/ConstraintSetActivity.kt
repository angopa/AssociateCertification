package com.angopa.aad.androidui.codelabs.constraintlayout

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import com.angopa.aad.R
import com.angopa.aad.databinding.ConstraintExampleMainBinding

private const val SHOW_BIG_IMAGE = "showBigImage"

/**
 *  Created by Andres Gonzalez on 2020-01-30.
 */
class ConstraintSetActivity : AppCompatActivity() {

    private lateinit var binding: ConstraintExampleMainBinding

    /**
     * Whether to show an enlarger image
     */
    private var showBigImage = false

    /**
     * The ConstraintLayout that any changes are applied to
     */
    private lateinit var rootLayout: ConstraintLayout

    /**
     * The ConstraintSet to use for the normal initial state
     */
    private val constraintSetNormal = ConstraintSet()

    /**
     * ConstraintSet to be applied on the normal ConstraintLayout to make the image bigger
     */
    private val constraintSetBig = ConstraintSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ConstraintExampleMainBinding>(
            this,
            R.layout.constraint_example_main
        ).apply {
            rootLayout = activityConstraintsetExample
            // Note that this can also be achieved by calling
            // `mConstraintSetNormal.load(this, R.layout.constraintset_example_main);`
            // Since we already have an inflated ConstraintLayout in `mRootLayout`, clone() is
            // faster and considered the best practice.
            constraintSetNormal.clone(rootLayout)
            constraintSetBig.load(this@ConstraintSetActivity, R.layout.constraint_example_big)

        }

        savedInstanceState?.let {
            val previous = it.getBoolean(SHOW_BIG_IMAGE)
            if (previous != showBigImage) {
                showBigImage = previous
                applyConfig()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SHOW_BIG_IMAGE, showBigImage)
    }

    fun toggleMode(view: View) {
        TransitionManager.beginDelayedTransition(rootLayout)
        showBigImage = !showBigImage
        applyConfig()
    }

    private fun applyConfig() {
        if (showBigImage) {
            constraintSetBig.applyTo(rootLayout)
        } else {
            constraintSetNormal.applyTo(rootLayout)
        }
    }
}
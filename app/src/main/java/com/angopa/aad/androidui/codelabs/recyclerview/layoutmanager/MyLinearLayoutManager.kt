package com.angopa.aad.androidui.codelabs.recyclerview.layoutmanager

import android.content.Context
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager

/**
 *  Created by Andres Gonzalez on 02/04/2020.
 *
 * Simple extension of LinearLayoutManager for the sole purpose of showing what happens
 * when predictive animations (which are enabled by default in LinearLayoutManager) are
 * not enabled. This behavior is toggled via a checkbox in the UI.
 */
class MyLinearLayoutManager(context: Context) : LinearLayoutManager(context) {

    private lateinit var predictiveCheckBox: CheckBox

    constructor(context: Context, predictiveCheckBox: CheckBox) : this(context) {
        this.predictiveCheckBox = predictiveCheckBox
    }

    override fun supportsPredictiveItemAnimations(): Boolean {
        return predictiveCheckBox.isChecked
    }
}
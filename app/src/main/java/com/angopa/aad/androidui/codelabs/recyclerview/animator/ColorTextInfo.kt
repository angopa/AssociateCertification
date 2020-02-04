package com.angopa.aad.androidui.codelabs.recyclerview.animator

import androidx.recyclerview.widget.RecyclerView

/**
 *  Created by Andres Gonzalez on 02/04/2020.
 *
 * Custom ItemHolderInfo class that holds color and text information used in
 * our custom change animation
 */
class ColorTextInfo: RecyclerView.ItemAnimator.ItemHolderInfo() {
    var color: Int = 0
    lateinit var text: String
}

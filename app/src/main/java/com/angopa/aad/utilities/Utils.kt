package com.angopa.aad.utilities

import android.graphics.Color
import kotlin.math.roundToInt

/**
 *  Created by Andres Gonzalez on 02/04/2020.
 */
class Utils {
    companion object {
        fun generateColor(): Int {
            val red = (Math.random() * 200).roundToInt()
            val green = (Math.random() * 200).roundToInt()
            val blue = (Math.random() * 200).roundToInt()
            return Color.rgb(red, green, blue)
        }
    }
}
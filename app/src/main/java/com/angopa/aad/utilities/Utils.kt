package com.angopa.aad.utilities

import android.graphics.Color
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
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

        /**
         * Return date in specified format.
         * @param milliSeconds Date in milliseconds
         * @return String representing date in specified format
         */
        fun formatDateFromMillis(milliSeconds: Long): String {
            // Create a DateFormatter object for displaying date in specified format.
            val formatter = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())

            return try {
                // Create a date object that will convert the date and time value in milliseconds to date.
                val date = Date(milliSeconds)

                formatter.format(date)
            } catch (ex: Exception) {
                Timber.d(ex)
                formatter.format(Date())
            }
        }

        private const val DATE_FORMAT = "MM/dd/yyyy hh:mm"
    }
}
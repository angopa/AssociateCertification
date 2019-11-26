package com.angopa.aad.utilities

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.angopa.aad.R
import com.angopa.aad.codelabs.fundamentals.activity.MultiplePurposeActivity
import com.angopa.aad.generated.callback.OnClickListener
import java.util.*

class DialogFactory(private val appConfiguration: AppConfiguration) {

    fun createCalendarDialog(
        context: Context,
        inflater: LayoutInflater,
        selectDateListener: MultiplePurposeActivity.SelectDateListener
    ): AlertDialog {
        var selectedDateString = ""
        return getBaseBuilder(context)
            .setView(inflater.inflate(R.layout.custom_calendar_view, null).apply {
                val selectedDate = findViewById<TextView>(R.id.date_text_view)
                findViewById<CalendarView>(R.id.calendar_view).apply {
                    setOnDateChangeListener { _, year, month, dayOfMonth ->
                        selectedDate.text = String.format("%s/%s/%s", month, dayOfMonth, year)
                        selectedDateString = selectedDate.text.toString()
                    }
                }
            })
            .setPositiveButton(OK_STRING) { _, _ ->
                selectDateListener.onSelectDateTapped(selectedDateString)
            }
            .setNegativeButton(CANCEL_STRING, null)
            .create()
    }

    private fun getBaseBuilder(context: Context): AlertDialog.Builder =
        AlertDialog.Builder(context).setCancelable(false)

    companion object {
        private const val OK_STRING: Int = R.string.dialog_label_ok
        private const val CANCEL_STRING: Int = R.string.dialog_label_cancel
    }
}
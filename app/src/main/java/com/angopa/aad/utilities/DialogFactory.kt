package com.angopa.aad.utilities

import android.content.Context
import android.view.LayoutInflater
import android.widget.CalendarView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import com.angopa.aad.R
import com.angopa.aad.androidcore.codelabs.fundamentals.activity.MultiplePurposeActivity
import com.angopa.aad.androidcore.codelabs.fundamentals.contentproviders.UserDictionaryFragment
import kotlinx.android.synthetic.main.custom_new_dictionary_word.view.*

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

    fun createNewWordDialog(
        context: Context,
        inflater: LayoutInflater,
        newWordListener: UserDictionaryFragment.NewWordListener
    ): AlertDialog {
        var newWord = ""
        return getBaseBuilder(context)
            .setView(inflater.inflate(R.layout.custom_new_dictionary_word, null).apply {
                findViewById<TextView>(R.id.new_dictionary_word).apply {
                    doAfterTextChanged { newWord = new_dictionary_word.text.toString() }
                }
            }).setPositiveButton(OK_STRING) {_,_ ->
                newWordListener.newWord(newWord)
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
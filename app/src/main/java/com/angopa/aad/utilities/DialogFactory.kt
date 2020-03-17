package com.angopa.aad.utilities

import android.content.Context
import android.view.LayoutInflater
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import com.angopa.aad.R
import com.angopa.aad.androidcore.codelabs.fundamentals.activity.MultiplePurposeActivity
import com.angopa.aad.androidcore.codelabs.fundamentals.contentproviders.UserDictionaryFragment
import kotlinx.android.synthetic.main.custom_dialog_new_user.view.*
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
            }).setPositiveButton(OK_STRING) { _, _ ->
                newWordListener.newWord(newWord)
            }
            .setNegativeButton(CANCEL_STRING, null)
            .create()
    }

    fun createInformationalDialog(context: Context, message: String, title: String): AlertDialog =
        getBaseBuilder(context)
            .setMessage(message)
            .setTitle(title)
            .setPositiveButton(OK_STRING, null)
            .create()

    fun createSettingOptionsDialog(
        context: Context,
        listener: SettingDialogOptionListener
    ): AlertDialog {
        val options = arrayOf("Report", "Delete", "Something else??", "No Valid")
        return getBaseBuilder(context)
            .setTitle("Sup!")
            .setItems(options) { _, which ->
                listener.optionSelected(which)
            }
            .create()
    }

    /**
     * Dialog which display two Edit Text asking for email and username, this should be place in a new
     * Activity but for simplicity I put it here
     */
    fun createNewUserDialog(
        context: Context,
        inflater: LayoutInflater,
        listener: FirebasePagingListener
    ): AlertDialog {
        var username = ""
        var email = ""
        return getBaseBuilder(context)
            .setView(inflater.inflate(R.layout.custom_dialog_new_user, null).apply {
                findViewById<EditText>(R.id.username_edit_text).apply {
                    doAfterTextChanged { username = username_edit_text.text.toString() }
                }
                findViewById<EditText>(R.id.email_edit_text).apply {
                    doAfterTextChanged { email = email_edit_text.text.toString() }
                }
            }).setPositiveButton(OK_STRING) { _, _ ->
                listener.createNewUser(username, email)
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

    interface SettingDialogOptionListener {
        fun optionSelected(@SettingOption int: Int)
    }

    interface FirebasePagingListener {
        fun createNewUser(username: String, email: String)
    }
}
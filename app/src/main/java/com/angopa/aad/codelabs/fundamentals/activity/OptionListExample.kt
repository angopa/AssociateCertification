package com.angopa.aad.codelabs.fundamentals.activity

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityOptionListExampleBinding

class OptionListExample : BaseActivity() {

    private lateinit var binding: ActivityOptionListExampleBinding

    private var result: String? = null

    override fun getScreenTitle(): Int = R.string.intent_filter_screen_label

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_option_list_example,
            null
        )

        binding.cancelButton.setOnClickListener {
            onCancelOptionSelected()
        }

        binding.selectButton.setOnClickListener {
            onOptionSelected()
        }
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            //Is the button now checked
            val checked = view.isChecked
            when (view.id) {
                R.id.options1 -> if (checked) result = "Options1"
                R.id.options2 -> if (checked) result = "Options2"
                R.id.options3 -> if (checked) result = "Options3"
                R.id.options4 -> if (checked) result = "Options4"
                else -> result = null
            }
        }
    }

    private fun onCancelOptionSelected() {
        Intent().apply {
            setResult(Activity.RESULT_CANCELED)
        }
        finish()
    }

    private fun onOptionSelected() {
        Intent().apply {
            putExtra(RESULT_KEY, result)
            setResult(Activity.RESULT_OK, this)
        }
        finish()
    }

    companion object {
        private const val RESULT_KEY = "result"
    }
}
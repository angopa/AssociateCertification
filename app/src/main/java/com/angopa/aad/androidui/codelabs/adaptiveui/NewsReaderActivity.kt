package com.angopa.aad.androidui.codelabs.adaptiveui

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.angopa.aad.R

/**
 *  Created by Andres Gonzalez on 02/06/2020.
 */
class NewsReaderActivity : FragmentActivity() {
    private var mIsDualPane: Boolean = false

    private var articleView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_reader)

        articleView = findViewById(R.id.article)
        mIsDualPane = articleView?.visibility == View.VISIBLE
    }

    fun updateCurrentArticle(view: View) {
        val tag = view.tag
        if (mIsDualPane) {
            articleView?.text = when (tag) {
                "sports" -> {
                    getString(R.string.sport_news)
                }
                "world" -> {
                    getString(R.string.world_news)
                }
                "finances" -> {
                    getString(R.string.finance_news)
                }
                else -> ""
            }
        } else {
            Toast.makeText(this, "Should display News Content in a new Activity", Toast.LENGTH_LONG)
                .show()
        }
    }
}
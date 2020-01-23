package com.angopa.aad.androidcore.codelabs.fundamentals.contentproviders

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.UserDictionary
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.ListFragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentProvidersUserDictionaryBinding
import com.angopa.aad.utilities.AppConfiguration
import com.angopa.aad.utilities.DialogFactory
import timber.log.Timber

// Defines a list of columns to retrieve from the Cursor and load into an output row
private val DICTIONARY_SUMMARY_PROJECTION: Array<String> = arrayOf(
    UserDictionary.Words.WORD,
    UserDictionary.Words.APP_ID,
    UserDictionary.Words.FREQUENCY,
    UserDictionary.Words.LOCALE,
    UserDictionary.Words._ID
)

class UserDictionaryFragment : ListFragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private lateinit var binding: FragmentProvidersUserDictionaryBinding

    private var searchString: String? = null

    private lateinit var adapter: SimpleCursorAdapter

    val dialogFactory = DialogFactory(AppConfiguration.get())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentProvidersUserDictionaryBinding>(
            inflater, R.layout.fragment_providers_user_dictionary, container, false
        ).apply {
            searchEditText.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    // Method left empty intentionally
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Method left empty intentionally
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    searchString = s.toString()
                    LoaderManager.getInstance(this@UserDictionaryFragment)
                        .restartLoader(0, null, this@UserDictionaryFragment)
                }
            })

            fab.setOnClickListener {
                displayNewWordDialog()
            }

            navControlBackButton.setOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        adapter = SimpleCursorAdapter(
            requireContext(),                           // The application's Context object
            android.R.layout.simple_list_item_2,        // A layout in XML for one row in the ListView
            null,                                    // The result from the query
            arrayOf(
                UserDictionary.Words.WORD,
                UserDictionary.Words._ID
            ),                                          // A String array of column names in the cursor
            intArrayOf(
                android.R.id.text1,
                android.R.id.text2
            ),                                          // An integer array of view ID's in the row layout
            0                                     // Flags (usually none are needed)
        )

        //Set the adapter for the ListView
        listAdapter = adapter

        LoaderManager.getInstance(this)
            .initLoader(0, null, this@UserDictionaryFragment)

        return binding.root
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        // If the word is the empty string, gets everything
        var selectionClause: String? = null

        // Declares an array to contain selection arguments
        val selectionArgs = searchString?.takeIf { it.isNotEmpty() }?.let {
            selectionClause = "${UserDictionary.Words.WORD} = ?"
            arrayOf(it)
        } ?: run {
            selectionClause = null
            emptyArray<String>()
        }

        Timber.d("SelectionArgs: ${selectionArgs.asList()}, SelectionClause: $selectionClause")

        return CursorLoader(
            (activity as Context),
            UserDictionary.Words.CONTENT_URI,
            DICTIONARY_SUMMARY_PROJECTION,
            selectionClause,
            selectionArgs,
            null
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        when (data?.count) {
            null -> Timber.e("Something went wrong when retrieving data.")
            0 -> Toast.makeText(requireContext(), "No results found", Toast.LENGTH_SHORT).show()
            else -> adapter.swapCursor(data)
        }
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapter.swapCursor(null)
    }

    private fun displayNewWordDialog() {
        dialogFactory.createNewWordDialog(requireContext(),
            LayoutInflater.from(requireContext()),
            object : NewWordListener {
                override fun newWord(word: String) {
                    insertNewWord(word)
                }
            }).show()
    }

    // Defines a new Uri object that receives the result of the insertion
    private var newUri: Uri? = null

    private fun insertNewWord(word: String) {
        // Defines an object to contain the new values to insert
        val newValues = ContentValues().apply {
            put(UserDictionary.Words.APP_ID, "com.angopa.aad")
            put(UserDictionary.Words.LOCALE, "en_US")
            put(UserDictionary.Words.WORD, word)
            put(UserDictionary.Words.FREQUENCY, "100")
        }

        Timber.d(newValues.toString())

        val contentResolver: ContentResolver = requireContext().contentResolver

        newUri = contentResolver.insert(
            UserDictionary.Words.CONTENT_URI,   // the user dictionary content URI
            newValues                           // the values to insert
        )

        Timber.d(newUri.toString())
    }

    interface NewWordListener {
        fun newWord(word: String)
    }
}
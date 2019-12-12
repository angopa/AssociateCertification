package com.angopa.aad.codelabs.fundamentals.contentproviders

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
                insertNewWord()
            }

            navControlBackButton.setOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_2,
            null,
            arrayOf(
                UserDictionary.Words.WORD,
                UserDictionary.Words._ID
            ),
            intArrayOf(
                android.R.id.text1,
                android.R.id.text2
            ),
            0
        )

        listAdapter = adapter

        LoaderManager.getInstance(this)
            .initLoader(0, null, this@UserDictionaryFragment)

        return binding.root
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        // If the word is the empty string, gets everything
        var selectionClause: String? = null

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

    // Defines a new Uri object that receives the result of the insertion
    private lateinit var newUri: Uri


    private fun insertNewWord() {
        // Defines an object to contain the new values to insert
        val newValues = ContentValues().apply {
            put(UserDictionary.Words.APP_ID, "com.angopa.aad")
            put(UserDictionary.Words.LOCALE, "en_US")
            put(UserDictionary.Words.WORD, "insert")
            put(UserDictionary.Words.FREQUENCY, "100")
        }

        Timber.d(newValues.toString())

        val contentResolver: ContentResolver = requireContext().contentResolver

        val uri = contentResolver.insert(
            UserDictionary.Words.CONTENT_URI,   // the user dictionary content URI
            newValues                           // the values to insert
        )

        Timber.d(uri.toString())
    }

}
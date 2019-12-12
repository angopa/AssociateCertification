package com.angopa.aad.codelabs.fundamentals.threads

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.*
import android.widget.ListView
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.ListFragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.findNavController
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentThreadLoaderBinding

private val CONTACTS_SUMMARY_PROJECTION: Array<String> = arrayOf(
    ContactsContract.Contacts._ID,
    ContactsContract.Contacts.DISPLAY_NAME,
    ContactsContract.Contacts.CONTACT_STATUS,
    ContactsContract.Contacts.CONTACT_PRESENCE,
    ContactsContract.Contacts.PHOTO_ID,
    ContactsContract.Contacts.LOOKUP_KEY
)

/**
 * Example of how to use LoaderManager, Loaders have been deprecated in API 28.
 * There is a problem when searching contacts with letters, I didn't solve I just put try-catch
 * since this is deprecated I didn't want to spend a lot of time trying to find out what was wrong
 */
class LoaderFragment :
    ListFragment(),
    SearchView.OnQueryTextListener,
    LoaderManager.LoaderCallbacks<Cursor> {
    private lateinit var binding: FragmentThreadLoaderBinding

    private lateinit var adapter: SimpleCursorAdapter

    // If non-null this is the current filter the user has provided
    private var curFilter: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentThreadLoaderBinding>(
            inflater, R.layout.fragment_thread_loader, container, false
        ).apply {
            navControlBackButton.setOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setHasOptionsMenu(true)

        // Create an empty adapter we will use to display the loaded data
        adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_2,
            null,
            arrayOf(
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.CONTACT_STATUS
            ),
            intArrayOf(android.R.id.text1, android.R.id.text2),
            0
        )

        listAdapter = adapter

        //Prepare the loader. Either re-connect with an existing one, or start a new one
        LoaderManager.getInstance(this)
            .initLoader(0, null, this@LoaderFragment)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Place an action bar item for searching
        menu.add("Search").apply {
            setIcon(android.R.drawable.ic_menu_search)
            setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
            actionView = SearchView(activity).apply {
                setOnQueryTextListener(this@LoaderFragment)
            }
        }
    }

    // Don't care about this
    override fun onQueryTextSubmit(query: String?): Boolean = true

    override fun onQueryTextChange(newText: String?): Boolean {
        // Called when the action bar search text has changed. Update the search filter, and restart
        // the loader to do a new query with this filter
        curFilter = if (newText?.isNotEmpty() == true) newText else null
        LoaderManager.getInstance(this)
            .restartLoader(0, null, this)
        return true
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        // This is called when a new Loader needs to be created. This sample only has one Loader,
        // so we don't care about the ID. First, pick the base URI to use depending on whether we are
        // currently filtering
        val baseUri: Uri = if (curFilter != null) {
            Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_URI, Uri.encode(curFilter))
        } else {
            ContactsContract.Contacts.CONTENT_URI
        }

        // Now Create and return a CursorLoader that will take care of creating a Cursor for
        // the data being displayed
        val select: String = "((${ContactsContract.Contacts.DISPLAY_NAME} NOTNULL) AND (" +
                "${ContactsContract.Contacts.HAS_PHONE_NUMBER} = 1 AND " +
                "${ContactsContract.Contacts.DISPLAY_NAME} != ''))"

        return CursorLoader(
            (activity as Context),
            baseUri,
            CONTACTS_SUMMARY_PROJECTION,
            select,
            null,
            "${ContactsContract.Contacts.DISPLAY_NAME} COLLATE LOCALIZED ASC"
        )
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        // Swap the new cursor in. (The framework will take care of closing the old
        // cursor once we return.)
        adapter.swapCursor(data)
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        //This is called when the last Cursor provided to onLoadFinished() above is about to
        // be closed. We need to make sure we are no longer using it
        adapter.swapCursor(null)
    }

    override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {
        val cursor = l.getItemAtPosition(position) as Cursor
        val itemId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID))
        val itemDisplayName =
            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
        val itemLookUp =
            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY))

        Toast.makeText(
            requireContext(),
            "$itemId: $itemDisplayName \n LOOKUP_KEY: $itemLookUp",
            Toast.LENGTH_SHORT
        ).show()

    }
}
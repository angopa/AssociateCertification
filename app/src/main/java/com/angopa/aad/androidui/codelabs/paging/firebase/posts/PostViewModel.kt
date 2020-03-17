package com.angopa.aad.androidui.codelabs.paging.firebase.posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.angopa.aad.androidui.codelabs.paging.firebase.Error
import com.angopa.aad.androidui.codelabs.paging.firebase.Success
import com.angopa.aad.androidui.codelabs.paging.firebase.SuccessMessage
import com.angopa.aad.androidui.codelabs.paging.firebase.ViewModelState
import com.angopa.aad.androidui.codelabs.paging.firebase.posts.model.Entry
import com.angopa.aad.androidui.codelabs.paging.firebase.posts.model.Metrics
import com.angopa.aad.androidui.codelabs.paging.firebase.posts.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import timber.log.Timber
import kotlin.random.Random

private const val TABLE_NAME_ENTRIES = "entries"
private const val TABLE_NAME_USERS = "users"
private const val TABLE_NAME_USER_ENTRIES = "user-entries"

/**
 *  Created by Andres Gonzalez on 03/13/2020.
 */
class FirebaseRealtimeDatabaseViewModel(
    private val databaseReference: DatabaseReference
) : ViewModel() {

    private val _insertState = MutableLiveData<ViewModelState>()
    val insertState: LiveData<ViewModelState>
        get() = _insertState

    private var currentUser: User? = null

    fun getUser() {
        databaseReference.child(TABLE_NAME_USERS)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Timber.d("Failed to read value %s", databaseError.message)
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val value = dataSnapshot.children
                    value.forEach { dataSnapshot ->
                        currentUser = dataSnapshot.getValue(User::class.java) as User
                        currentUser?.id = dataSnapshot.key
                        Timber.d("User: %s", currentUser)
                    }
                    writeNewEntry(Entry())
                }

            })
    }

    fun getEntries(): ArrayList<Entry>? {
        databaseReference.child(TABLE_NAME_ENTRIES)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(databaseError: DatabaseError) {
                    Timber.d("Failed to read value %s", databaseError.message)
                }

                override fun onDataChange(databaseSnapshot: DataSnapshot) {
                    val values = databaseSnapshot.children
                    values.forEach { dataSnapshot ->
                        val entry: Entry = dataSnapshot.getValue(
                            Entry::class.java
                        ) as Entry
                        entry.postId = dataSnapshot.key
                        Timber.d("Value is: %s", entry)
                    }
                }

            })
        return null
    }

    fun writeNewEntry(entry: Entry) {
        // Create new post at /entries/$entryId and create a post for
        // /user-entries/$userId/$entryId
        val key = databaseReference.child("entries").push().key
        Timber.d("Key: %s", key)
        if (key == null) {
            Timber.d("Couldn't get push key for posts")
            return
        }

        val metrics = Metrics(Random.nextInt(10).toLong())
        val entry =
            Entry(
                "Hi, from new post",
                System.currentTimeMillis(),
                null,
                metrics,
                null,
                "post",
                currentUser?.id
            )
        val entryValues = entry.toMap()

        val childUpdates = HashMap<String, Any?>()

        childUpdates["/$TABLE_NAME_ENTRIES/$key"] = entryValues
        childUpdates["/$TABLE_NAME_USER_ENTRIES/${currentUser?.id}/$key"] = entryValues


        databaseReference.updateChildren(childUpdates)
            .addOnSuccessListener {
                _insertState.value =
                    Success
            }.addOnFailureListener { exception ->
                _insertState.value =
                    Error(
                        exception.toString()
                    )
            }
    }

    fun createNewUser(username: String, email: String) {
        // Create new user into /users/$userId
        val key = databaseReference.child("users").push().key
        Timber.d("New User Key: $key")
        if (key == null) {
            Timber.d("Couldn't get push key for posts")
            return
        }

        val user = User(username = username, email = email)
        val userValues = user.toMap()
        val childUpdates = HashMap<String, Any?>()

        childUpdates["/$TABLE_NAME_USERS/$key"] = userValues

        databaseReference.updateChildren(childUpdates)
            .addOnSuccessListener {
                _insertState.value =
                    SuccessMessage(
                        "User Created Successfully!"
                    )
            }.addOnFailureListener { exception ->
                _insertState.value =
                    Error(
                        exception.toString()
                    )
            }
    }
}
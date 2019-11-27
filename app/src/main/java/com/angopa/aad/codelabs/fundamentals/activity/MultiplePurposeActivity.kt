package com.angopa.aad.codelabs.fundamentals.activity

import android.Manifest.permission.READ_CONTACTS
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Intent
import android.content.Intent.*
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import com.angopa.aad.BaseActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.ActivityMultiplePurposeBinding
import com.angopa.aad.utilities.AppConfiguration
import com.angopa.aad.utilities.DialogFactory
import com.angopa.aad.utilities.InjectorUtils
import com.angopa.aad.utilities.PermissionUtil
import com.angopa.aad.utilities.PermissionUtil.PermissionState
import com.angopa.aad.viewmodel.MultiplePurposeActivityViewModel
import com.bumptech.glide.Glide
import timber.log.Timber

class MultiplePurposeActivity : BaseActivity() {

    private lateinit var binding: ActivityMultiplePurposeBinding

    private var readContactPermissionState: PermissionState? = null

    private var accessMediaPermissionState: PermissionState? = null

    private val viewModel: MultiplePurposeActivityViewModel by viewModels {
        InjectorUtils.provideMultiplePurposeActivityViewModelFactory(this@MultiplePurposeActivity)
    }

    private var contactUri: String = ""

    private var imageUri: String = ""

    override fun getBindingComponent() {
        binding = DataBindingUtil.setContentView<ActivityMultiplePurposeBinding>(
            this, R.layout.activity_multiple_purpose
        ).apply {
            callback = object : Callback {
                override fun startActivityForResultContact() = launchContactActivity()
                override fun startActivityForResultImage() = launchImageActivity()
                override fun displayCalendarDialog() = displayCalendar()
                override fun savePreferences() = saveSelectedPreferences()
                override fun sendEmailWithGmail() = onSendEmailWithGmail()
                override fun sendEmailWithImplicitActivity() = openSelectActivityBar()
                override fun launchSelectOptionActivity() = onLaunchSelectOptionActivity()
                override fun launchTestIntent() = testIntentCapabilities()
            }
        }

        //Implement Lifecycle Listener.
        lifecycle.addObserver(MyObserver())

        subscribeUi()
    }

    /**
     * Restore values after the activity is destroy for any configuration change, like device rotation,
     * you can implement this in onCreate since both receive the same bundle, but in onCreate you have
     * to check if bundle != null, and onRestoreInstanceState is call only if is not null
     */
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState)

        // Restore state members from saved instance
        with(savedInstanceState) {
            binding.textMessage.setText(getString(BUNDLE_EXTRA_MESSAGE))
            binding.optionSelectedTextView.text = getString(BUNDLE_EXTRA_SELECTED_OPTION)
        }
    }

    private fun subscribeUi() {
        displayContactInformationIfExist(viewModel.activityModel.contactUri.toUri())
    }

    override fun getScreenTitle(): Int = R.string.label_activity_codelab

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_CONTACT_REQUEST -> {
                    displayContactInformationIfExist(data?.data)
                }
                PICK_IMAGE_REQUEST -> {
                    displayImageIfExist(data?.data)
                }
                PICK_OPTION_FROM_LIST_REQUEST -> {
                    displaySelectedOption(data)
                }
            }
        }
    }

    private fun displaySelectedOption(data: Intent?) {
        if (data?.extras != null) {
            binding.optionSelectedTextView.text = data.getStringExtra("result")
        }
    }

    private fun launchContactActivity() {
        startActivityForResult(
            Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI),
            PICK_CONTACT_REQUEST
        )
    }

    private fun launchImageActivity() {
        startActivityForResult(
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
            PICK_IMAGE_REQUEST
        )
    }

    private fun displayCalendar() {
        showAlertDialog(
            DialogFactory(AppConfiguration.get()).createCalendarDialog(
                this@MultiplePurposeActivity,
                LayoutInflater.from(this@MultiplePurposeActivity),
                object : SelectDateListener {
                    override fun onSelectDateTapped(date: String) {
                        binding.selectedDateEditText.setText(date)
                    }
                }
            )
        )
    }

    private fun saveSelectedPreferences() {
        viewModel.saveSelections(contactUri, imageUri)
    }

    private fun updateUserName(userName: String) {
        binding.resultUserNameEditText.setText(userName)
    }

    private fun displayImage(imageBitmap: Bitmap) {
        Glide.with(this@MultiplePurposeActivity)
            .load(imageBitmap)
            .centerCrop()
            .into(binding.resultImageImageView)
    }

    private fun displayImageIfExist(uri: Uri?) {
        if (!PermissionState.GRANTED.equals(accessMediaPermissionState)) tryRequestAccessMediaPermission()
        imageUri = uri.toString()
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        displayImage(bitmap)
    }

    private fun displayContactInformationIfExist(uri: Uri?) {
        if (!PermissionState.GRANTED.equals(readContactPermissionState)) tryRequestingReadContactPermission()
        contactUri = uri.toString()
        val cursor = contentResolver.query(uri!!, null, null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            updateUserName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)))
        }
    }

    private fun tryRequestAccessMediaPermission() {
        if (getAccessMediaPermission() == PermissionState.GRANTED) return
        ActivityCompat.requestPermissions(
            this@MultiplePurposeActivity,
            arrayOf(READ_EXTERNAL_STORAGE), PERMISSIONS_REQUEST_ACCESS_MEDIA
        )
    }

    private fun tryRequestingReadContactPermission() {
        if (getReadContactPermissionState() == PermissionState.GRANTED) return
        ActivityCompat.requestPermissions(
            this@MultiplePurposeActivity,
            arrayOf(READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS
        )
    }

    private fun getReadContactPermissionState(): PermissionState =
        PermissionUtil.getPermissionState(this, READ_CONTACTS)

    private fun getAccessMediaPermission(): PermissionState =
        PermissionUtil.getPermissionState(this, READ_EXTERNAL_STORAGE)

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_CONTACTS -> {
                readContactPermissionState = getReadContactPermissionState()
                notifyLocationPermissionRequestResolved()
            }

            PERMISSIONS_REQUEST_ACCESS_MEDIA -> {
                accessMediaPermissionState = getAccessMediaPermission()
                notifyAccessMediaPermissionRequestResolved()
            }
        }
    }

    private fun notifyLocationPermissionRequestResolved() {
        if (PermissionState.GRANTED.equals(readContactPermissionState)) {
            displayContactInformationIfExist(viewModel.activityModel.contactUri.toUri())
        }
    }

    private fun notifyAccessMediaPermissionRequestResolved() {
        if (PermissionState.GRANTED.equals(accessMediaPermissionState)) {
            displayImageIfExist(viewModel.activityModel.imageUri.toUri())
        }
    }

    private fun onLaunchSelectOptionActivity() {
        startActivityForResult(
            Intent(this, OptionListExample::class.java),
            PICK_OPTION_FROM_LIST_REQUEST
        )
    }

    private fun onSendEmailWithGmail() {
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            type = "text/plain"
            setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail")
            putExtra(Intent.EXTRA_EMAIL, "text@doamin.com")
            putExtra(Intent.EXTRA_SUBJECT, "Test")
            putExtra(Intent.EXTRA_TEXT, binding.textMessage.text)
        }
        startActivity(intent)
    }


    private fun openSelectActivityBar() {
        startActivity(Intent().apply {
            action = Intent.ACTION_SEND
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, binding.textMessage.text)
        })
    }

    /**
     * If called, this method will occur after onStop() for applications targeting platforms
     * starting with Build.VERSION_CODES.P. For applications targeting earlier platform
     * versions this method will occur before onStop() and there are no guarantees about
     * whether it will occur before or after onPause().
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString(BUNDLE_EXTRA_MESSAGE, binding.textMessage.text.toString())
            putString(BUNDLE_EXTRA_SELECTED_OPTION, binding.optionSelectedTextView.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    /**
     * Should start components needed just one time during the lifecycle of the app, start binding,
     * set layout.
     *
     *  @param savedInstanceState variable receive just if (it != null) contains values saved on
     *  onSaveInstanceState()
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate(savedInstanceState: Bundle?) - next call onStart()")
    }

    /**
     * Should init components that will show information to the user, in this state the activity
     * is visible to the user.
     */
    override fun onStart() {
        super.onStart()
        Timber.d("onStart() - next call onResume()")
    }

    /**
     * App is in foreground and in top of the stack, receiving all user interaction
     * should init componets needed when the user is interacting with the app like animations
     */
    override fun onResume() {
        super.onResume()
        Timber.d("onResume() - next call onPause()")
    }

    /**
     * Invoke when another app appear on top of it, but it still visible to the user
     * Destroy/Stop components created on onResume()
     */
    override fun onPause() {
        Timber.d("onPause() - can call onRestart() or onStop()")
        super.onPause()
    }

    /**
     * Invoke when the app is not visible to the user, at this point, we should destroy/stop
     * components call when onStart() occurs,
     */
    override fun onStop() {
        Timber.d("onStop() - can call onResume() or onDestroy()")
        super.onStop()
    }

    /**
     * Call when the app is returning from background to foreground.
     */
    override fun onRestart() {
        super.onRestart()
        Timber.d("onRestart() - nex call is onStart()")
    }

    /**
     * Call when the process holding the app is kill by the garbage collector or if the user
     * finis() it
     */
    override fun onDestroy() {
        Timber.d("onDestroy()")
        super.onDestroy()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Timber.d("onConfigurationChanged(newConfig: Configuration)")
    }

    interface Callback {
        fun startActivityForResultContact()
        fun startActivityForResultImage()
        fun displayCalendarDialog()
        fun savePreferences()
        fun sendEmailWithGmail()
        fun sendEmailWithImplicitActivity()
        fun launchSelectOptionActivity()
        fun launchTestIntent()
    }

    interface SelectDateListener {
        fun onSelectDateTapped(date: String)
    }

    companion object {
        private const val PICK_CONTACT_REQUEST = 0
        private const val PICK_IMAGE_REQUEST = 1
        private const val PICK_OPTION_FROM_LIST_REQUEST = 2

        private const val PERMISSIONS_REQUEST_READ_CONTACTS = 9
        private const val PERMISSIONS_REQUEST_ACCESS_MEDIA = 8

        private const val BUNDLE_EXTRA_MESSAGE = "MultiplePurposeActivity.BUNDLE_EXTRA_MESSAGE"
        private const val BUNDLE_EXTRA_SELECTED_OPTION =
            "MultiplePurposeActivity.BUNDLE_EXTRA_SELECTED_OPTION"
    }

    private fun testIntentCapabilities() {
        val sendIntent = Intent().apply {
            action = ACTION_SEND
            putExtra(EXTRA_TEXT, "This is a extra message")
            type = "text/plain"
        }

        // Always open the selector bottom bar, the user has to select the app that will open the
        // Intent every time this option is launch
        val chooser = createChooser(sendIntent, "Share info with")

        // Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        }
    }
}
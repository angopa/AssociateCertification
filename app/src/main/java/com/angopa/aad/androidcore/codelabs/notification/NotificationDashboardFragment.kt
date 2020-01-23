package com.angopa.aad.androidcore.codelabs.notification

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.angopa.aad.MainActivity
import com.angopa.aad.R
import com.angopa.aad.databinding.FragmentNotificationDashboardBinding
import com.angopa.aad.utilities.IMPORTANT_NOTIFICATION_CHANNEL_ID
import com.angopa.aad.utilities.LOW_NOTIFICATION_CHANNEL_ID
import com.angopa.aad.utilities.REGULAR_NOTIFICATION_CHANNEL_ID
import java.util.*

class NotificationDashboardFragment : Fragment() {
    private lateinit var binding: FragmentNotificationDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentNotificationDashboardBinding>(
            inflater,
            R.layout.fragment_notification_dashboard,
            container,
            false
        ).apply {
            basicNotificationButton.setOnClickListener { displayBasicNotification() }
            expandableNotificationButton.setOnClickListener { displayExpandableNotification() }
            intentNotificationButton.setOnClickListener { displayIntentNotification() }
            replyNotificationButton.setOnClickListener { displayReplyNotification() }
            progressBarNotificationButton.setOnClickListener { displayProgressBar() }
            progressBarInfiniteNotificationButton.setOnClickListener { displayInfiniteProgressBar() }
            messagingStyleNotificationButton.setOnClickListener { displayMessagingStyleNotification() }
            mediaTypeNotificationButton.setOnClickListener { displayMediaTypeNotification() }
            highPriorityNotificationButton.setOnClickListener { displayHighPriorityNotification() }
            taskStackNotificationButton.setOnClickListener { displayNotificationWithTaskStack() }
            emptyTaskStackNotificationButton.setOnClickListener { displayNotificationWithEmptyTaskStack() }
            groupNotificationButton.setOnClickListener { displayGroupNotification() }
            customNotificationButton.setOnClickListener { displayCustomNotification() }
            openNotificationChannelButton.setOnClickListener {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    redirectToNotificationSettings()
                }
            }
        }

        return binding.root
    }

    private fun displayBasicNotification() {
        val builder = obtainBasicConfiguration()
        showNotification(builder)
    }

    private fun displayExpandableNotification() {
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_image)
        val builder = obtainBasicConfiguration()
            .setStyle(
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
                    .bigLargeIcon(null)
            )
            .setLargeIcon(bitmap)

        showNotification(builder)
    }

    private fun displayIntentNotification() {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(requireContext(), MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(requireContext(), 0, intent, 0)

        val builder = obtainBasicConfiguration()
            .setContentIntent(pendingIntent) // Set the intent that will fire when user taps the notification
            .setAutoCancel(true)

        showNotification(builder)
    }

    private fun displayReplyNotification() {
        val replyLabel: String = resources.getString(R.string.reply_label)
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_TEXT_REPLY).run {
            setLabel(replyLabel)
            build()
        }

        // Build a PendingIntent for the reply action to trigger
        val notificationIntent = Intent(requireContext(), NotificationPublisher::class.java)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, NOTIFICATION_REPLY_ID)

        val replyPendingIntent: PendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val action: NotificationCompat.Action = NotificationCompat.Action.Builder(
            R.drawable.ic_notification,
            getString(R.string.generic_label_reply),
            replyPendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()

        //Build the notification and add the action
        val builder = obtainBasicConfiguration()
            .addAction(action)

        with(NotificationManagerCompat.from(requireContext())) {
            notify(NOTIFICATION_REPLY_ID, builder.build())
        }
    }

    private fun displayProgressBar() {
        val builder = NotificationCompat.Builder(requireContext(), LOW_NOTIFICATION_CHANNEL_ID)
            .setContentTitle(getString(R.string.download_notification_title))
            .setContentText(getString(R.string.download_notification_text))
            .setSmallIcon(R.drawable.ic_download)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setTimeoutAfter(CANCEL_TIMEOUT)

        NotificationManagerCompat.from(requireContext()).apply {
            builder.setProgress(PROGRESS_MAX, PROGRESS_CURRENT, false)
            notify(PROGRESS_NOTIFICATION_ID, builder.build())

            Thread(Runnable {
                for (x in 0 until 100) {
                    Thread.sleep(100)
                    builder.setProgress(PROGRESS_MAX, x, false)
                    notify(PROGRESS_NOTIFICATION_ID, builder.build())
                }

                builder.setContentText(getString(R.string.download_notification_complete))
                    .setProgress(0, 0, false)
                    .setTimeoutAfter(CANCEL_TIMEOUT)
                notify(PROGRESS_NOTIFICATION_ID, builder.build())
            }).start()
        }
    }

    private fun displayInfiniteProgressBar() {
        val builder = NotificationCompat.Builder(requireContext(), LOW_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_download)
            .setContentTitle(getString(R.string.download_notification_title))
            .setContentText(getString(R.string.download_notification_text))
            .setPriority(NotificationCompat.PRIORITY_LOW)

        NotificationManagerCompat.from(requireContext()).apply {
            builder.setProgress(0, 0, true)
            notify(PROGRESS_NOTIFICATION_ID, builder.build())

            Thread(Runnable {
                for (x in 0 until 100) {
                    Thread.sleep(100)
                }

                builder.setContentText(getString(R.string.download_notification_complete))
                    .setProgress(0, 0, false)
                    .setTimeoutAfter(CANCEL_TIMEOUT)
                notify(PROGRESS_NOTIFICATION_ID, builder.build())
            }).start()
        }
    }

    private fun displayMessagingStyleNotification() {
        val date = Date()
        val time = date.time
        val message = NotificationCompat.MessagingStyle.Message("message 1", time, "Me")
        val message1 = NotificationCompat.MessagingStyle.Message("message 2", time, "Coworker")
        val message2 = NotificationCompat.MessagingStyle.Message("message 3", time, "Me")
        val message3 = NotificationCompat.MessagingStyle.Message("message 4", time, "Coworker")

        val builder = NotificationCompat.Builder(requireContext(), REGULAR_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setStyle(
                NotificationCompat.MessagingStyle("Title")
                    .setConversationTitle("Time Lunch")
                    .addMessage(message)
                    .addMessage(message1)
                    .addMessage(message2)
                    .addMessage(message3)
            )

        showNotification(builder)
    }

    private fun displayMediaTypeNotification() {
        var builder = NotificationCompat.Builder(requireContext(), REGULAR_NOTIFICATION_CHANNEL_ID)
            //Show controls on lock screen even when user hides sensitive content.
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.drawable.ic_notification)
            //Add media control buttons that invoke intents in your media service
            .addAction(R.drawable.ic_previous, "Previous", null)
            .addAction(R.drawable.ic_pause, "Pause", null)
            .addAction(R.drawable.ic_next, "Next", null)
            //Apply the media style template
            .setContentTitle("Music App")
            .setContentText("Playing now")
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    resources,
                    R.drawable.test_image
                )
            )

        showNotification(builder)
    }

    private fun displayHighPriorityNotification() {
        val fullScreenIntent = Intent(requireContext(), FullScreenActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            requireContext(),
            0,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder =
            NotificationCompat.Builder(requireContext(), IMPORTANT_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.notification_title))
                .setContentText("(456) 987-1235")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setAutoCancel(true)
                // Use a full-screen intent only for the highest-priority alerts where you
                // have an associated activity that would like to launch after the user interacts
                // with the notification. Also, if your app targets Android 10 or higher,
                // you need to request the USE_FULL_SCREEN_INTENT permission in order for the
                // platform to invoke this notification
                .setFullScreenIntent(pendingIntent, true)

        showNotification(builder)
    }

    private fun displayNotificationWithTaskStack() {
        // Create an Intent for the activity you want to start
        val resultIntent = Intent(requireContext(), FullScreenActivity::class.java)
        //Create the TaskStackBuilder
        val resultPendingIntent: PendingIntent = TaskStackBuilder.create(requireContext()).run {
            // Add the Intent which inflates the back stack
            addNextIntentWithParentStack(resultIntent)
            //Get the PendingIntent containing the entire back stack
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val builder = NotificationCompat.Builder(requireContext(), REGULAR_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_back_stack_text))
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        showNotification(builder)
    }

    private fun displayNotificationWithEmptyTaskStack() {
        val notifyIntent = Intent(requireContext(), EmptyTaskActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            requireContext(),
            0,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(requireContext(), REGULAR_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.notification_empty_stack_text))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        showNotification(builder)
    }

    private fun displayGroupNotification() {
        val groupBuilder =
            NotificationCompat.Builder(requireContext(), REGULAR_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.notification_title))
                .setGroupSummary(true)
                .setGroup(GROUP_KEY_WORK_EMAIL)
                .setStyle(
                    NotificationCompat.InboxStyle()
                        .setSummaryText("email@example.com")
                )

        val builder = NotificationCompat.Builder(requireContext(), REGULAR_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(
                String.format(
                    getString(R.string.notification_number_label),
                    Random().nextInt()
                )
            )
            .setGroup(GROUP_KEY_WORK_EMAIL)

        NotificationManagerCompat.from(requireContext()).apply {
            notify(NOTIFICATION_ID_GENERATOR.nextInt(), builder.build())
            notify(GROUP_ID, groupBuilder.build())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun redirectToNotificationSettings() {
        val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, requireContext().packageName)
            putExtra(Settings.EXTRA_CHANNEL_ID, IMPORTANT_NOTIFICATION_CHANNEL_ID)
        }

        startActivity(intent)
    }

    private fun displayCustomNotification() {
        // Get the layouts to use in the custom notification
        val notificationLayout = RemoteViews(requireContext().packageName, R.layout.custom_notification_small)
        val notificationLayoutExpanded = RemoteViews(requireContext().packageName, R.layout.custom_notification_large)

        // Apply the layouts to the notification
        val builder = NotificationCompat.Builder(requireContext(), REGULAR_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            // If remove setStyle you have to set your own headers and styles
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutExpanded)

        showNotification(builder)
    }

    private fun obtainBasicConfiguration(): NotificationCompat.Builder {
        return NotificationCompat.Builder(requireContext(), REGULAR_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(getString(R.string.notification_title))
            .setContentText(getString(R.string.lorem_ipsum))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setTimeoutAfter(CANCEL_TIMEOUT)
    }

    private fun showNotification(notification: NotificationCompat.Builder) {
        with(NotificationManagerCompat.from(requireContext())) {
            notify(NOTIFICATION_ID_GENERATOR.nextInt(), notification.build())
        }
    }

    companion object {
        private val NOTIFICATION_ID_GENERATOR = Random()

        // Key for the string that's delivered in the action's intent
        const val KEY_TEXT_REPLY = "key_text_reply"
        const val NOTIFICATION_REPLY_ID = 101

        const val PROGRESS_MAX = 100
        const val PROGRESS_CURRENT = 0
        const val PROGRESS_NOTIFICATION_ID = 202

        const val CANCEL_TIMEOUT = 10000L

        const val GROUP_KEY_WORK_EMAIL = "NotificationDashboardFragment.GROUP_KEY_WORK_EMAIL"

        const val GROUP_ID = 1001
    }
}
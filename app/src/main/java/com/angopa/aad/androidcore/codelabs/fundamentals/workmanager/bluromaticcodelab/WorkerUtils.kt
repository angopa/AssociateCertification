package com.angopa.aad.androidcore.codelabs.fundamentals.workmanager.bluromaticcodelab

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.renderscript.Allocation
import androidx.renderscript.Element
import androidx.renderscript.RenderScript
import androidx.renderscript.ScriptIntrinsicBlur
import com.angopa.aad.R
import com.angopa.aad.utilities.DELAY_TIME_MILLIS
import com.angopa.aad.utilities.OUTPUT_PATH
import com.angopa.aad.utilities.REGULAR_NOTIFICATION_CHANNEL_ID
import timber.log.Timber
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class WorkerUtils {
    companion object {
        /**
         * Create a Notification that is shown as a heads-up notification if possible.
         * <p>
         * For this codelab, this is used to show a notification so that you know when different steps
         * of the background work chain are starting
         *
         * @param message Message shown on the notification
         * @param context Context needed to create Toast
         */
        fun makeStatusNotification(message: String, context: Context) {
            // Create the notification
            val builder = NotificationCompat.Builder(context, REGULAR_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(message)

            // Show the notification
            NotificationManagerCompat.from(context).notify(0, builder.build())
        }

        /**
         * Method for sleeping for a fixed about of time to emulate slower work
         */
        fun sleep() {
            try {
                Thread.sleep(DELAY_TIME_MILLIS, 0)
            } catch (e: InterruptedException) {
                Timber.d(e.message)
            }
        }

        /**
         * Blurs the given Bitmap image
         *
         * @param bitmap             Image to blur
         * @param applicationContext Application context
         * @return Blurred bitmap image
         */
        fun blurBitmap(bitmap: Bitmap, context: Context): Bitmap {
            var rsContext: RenderScript? = null
            return try {
                // Create the output bitmap
                val output = Bitmap.createBitmap(
                    bitmap.width, bitmap.height, bitmap.config
                )

                // Blur the image
                rsContext = RenderScript.create(
                    context,
                    RenderScript.ContextType.DEBUG
                )
                val inAlloc = Allocation.createFromBitmap(rsContext, bitmap)
                val outAlloc = Allocation.createTyped(rsContext, inAlloc.type)
                val theIntrinsic =
                    ScriptIntrinsicBlur.create(rsContext, Element.U8_4(rsContext))

                theIntrinsic.setRadius(10f)
                theIntrinsic.setInput(inAlloc)
                theIntrinsic.forEach(outAlloc)
                outAlloc.copyTo(output)
                output
            } finally {
                rsContext?.finish()
            }
        }

        /**
         * Writes bitmap to a temporary file and returns the Uri for the file
         *
         * @param applicationContext Application context
         * @param bitmap             Bitmap to write to temp file
         * @return Uri for temp file with bitmap
         * @throws FileNotFoundException Throws if bitmap file cannot be found
         */
        fun writeBitmapToFile(context: Context, bitmap: Bitmap): Uri? {
            val name = String.format("blur-filter-output-%s.png", UUID.randomUUID().toString())
            val outputDir = File(context.getFilesDir(), OUTPUT_PATH)
            if (!outputDir.exists()) {
                outputDir.mkdirs() // should succeed
            }

            val outputFile = File(outputDir, name)
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(outputFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
            } finally {
                if (out != null) {
                    try {
                        out.close()
                    } catch (ignore: IOException) {
                    }
                }
            }
            return Uri.fromFile(outputFile)
        }
    }
}
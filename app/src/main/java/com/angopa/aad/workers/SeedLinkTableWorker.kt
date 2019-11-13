package com.angopa.aad.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.angopa.aad.data.AppDatabase
import com.angopa.aad.data.Link
import com.angopa.aad.utilities.LINK_DATA_FILENAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class SeedLinkTableWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(LINK_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val linkType = object : TypeToken<List<Link>>() {}.type
                    val linkList: List<Link> = Gson().fromJson(jsonReader, linkType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.linkDao().insertAll(linkList)
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Timber.e(ex, "Error seeding table Link")
            Result.failure()
        }
    }
}
package com.angopa.aad.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.angopa.aad.data.localdata.AppDatabase
import com.angopa.aad.data.localdata.Post
import com.angopa.aad.utilities.POST_DATA_FILENAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

/**
 *  Created by Andres Gonzalez on 02/05/2020.
 */
class SeedPostTableWorker(
    context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(POST_DATA_FILENAME).use { inputSteam ->
                JsonReader(inputSteam.reader()).use { jsonReader ->
                    val tokenType = object : TypeToken<List<Post>>() {}.type
                    val postList: List<Post> = Gson().fromJson(jsonReader, tokenType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.postDao().insertAll(postList)
                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Timber.e(ex, "Error seeding table Link")
            Result.failure()
        }
    }
}
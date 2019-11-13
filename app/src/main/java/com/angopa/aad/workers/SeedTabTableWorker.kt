package com.angopa.aad.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.angopa.aad.data.AppDatabase
import com.angopa.aad.data.Tab
import com.angopa.aad.utilities.TAB_DATA_FILENAME
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class SeedTabTableWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open(TAB_DATA_FILENAME).use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val tabType = object : TypeToken<List<Tab>>() {}.type
                    val tabList: List<Tab> = Gson().fromJson(jsonReader, tabType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.tabDao().insertAll(tabList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Timber.e(ex, "Error seeding table Tab")
            Result.failure()
        }
    }
}
package com.angopa.aad.codelabs.fundamentals.workermanager

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import androidx.work.testing.TestWorkerBuilder
import androidx.work.workDataOf
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.ListenableSleepWorker
import com.angopa.aad.codelabs.fundamentals.workmanager.workers.SleepWorker
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@RunWith(AndroidJUnit4::class)
class ListenableSleepWorkerTest {
    private lateinit var context: Context
    private lateinit var executor: Executor

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        executor = Executors.newSingleThreadExecutor()
    }

    @Test
    fun testSleepWorker() {
        // Kotlin code can use the TestListenableWorkerBuilder extension to
        // build the ListenableWorker
        val worker = TestListenableWorkerBuilder<ListenableSleepWorker>(context)
            .build()

        runBlocking {
            val result = worker.doWork()
            assertThat(result, `is`(ListenableWorker.Result.success()))
        }
    }

    @Test
    fun testSleepWorkerExecutor() {
        val worker = TestWorkerBuilder<SleepWorker>(
            context,
            executor,
            inputData = workDataOf("SLEEP_DURATION" to 1000L)
        ).build()

        val result = worker.doWork()
        assertThat(result, `is`(ListenableWorker.Result.success()))
    }
}

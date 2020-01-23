package com.angopa.aad.androidcore.codelabs.fundamentals.workermanager

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.*
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.angopa.aad.androidcore.codelabs.fundamentals.workmanager.workers.EchoWorker
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class WorkerManagerTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        val config = Configuration.Builder()
            // Set log level to Log.DEBUG to make it easier to debug
            .setMinimumLoggingLevel(Log.DEBUG)
            // Use a SynchronousExecutor here to make it easier to write test
            .setExecutor(SynchronousExecutor())
            .build()

        // Initial WorkManager for instrumentation tests
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }


    @Test
    @Throws(Exception::class)
    fun testSimpleEchoWorker() {
        // Define input data
        val inputData = workDataOf(KEY_1 to 1, KEY_2 to 2)

        //Create request
        val request = OneTimeWorkRequestBuilder<EchoWorker>()
            .setInputData(inputData)
            .build()

        val workManager = WorkManager.getInstance(context)
        // Enqueue and wait for result. This also runs the Worker synchronously
        // because we are using a SynchronousExecutor
        workManager.enqueue(request).result.get()
        // Get WorkInfo and outputData
        val workInfo = workManager.getWorkInfoById(request.id).get()
        val outputData = workInfo.outputData
        // Assert
        assertThat(workInfo.state, `is`(WorkInfo.State.SUCCEEDED))
        assertThat(outputData, `is`(inputData))
    }

    @Test
    @Throws(Exception::class)
    fun testEchoWorkerNoInput() {
        // Create request
        val request = OneTimeWorkRequestBuilder<EchoWorker>()
            .build()

        val workManager = WorkManager.getInstance(context)
        // Enqueue and wait for result. This also runs the Worker synchronously
        // because we are using a SynchronousExecutor
        workManager.enqueue(request).result.get()
        // Get WorkInfo
        val workInfo = workManager.getWorkInfoById(request.id).get()
        // Assert
        assertThat(workInfo.state, `is`(WorkInfo.State.FAILED))
    }

    @Test
    @Throws(Exception::class)
    fun testWithInitialDelay() {
        // Define inputData
        val inputData = workDataOf(KEY_1 to 1, KEY_2 to 2)

        // Create request
        val request = OneTimeWorkRequestBuilder<EchoWorker>()
            .setInputData(inputData)
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()

        val workManager = WorkManager.getInstance(context)
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)
        // Enqueue and wait for result
        workManager.enqueue(request).result.get()
        testDriver?.setInitialDelayMet(request.id)

        // Get WorkInfo and output
        val workInfo = workManager.getWorkInfoById(request.id).get()
        val outputData = workInfo.outputData

        // Assert
        assertThat(workInfo.state, `is`(WorkInfo.State.SUCCEEDED))
        assertThat(outputData, `is`(inputData))
    }

    @Test
    @Throws(Exception::class)
    fun testWithConstraints() {
        // Define inputData
        val inputData = workDataOf(KEY_1 to 1, KEY_2 to 2)

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresDeviceIdle(true)
            .build()

        // Create request
        val request = OneTimeWorkRequestBuilder<EchoWorker>()
            .setConstraints(constraints)
            .setInitialDelay(10, TimeUnit.SECONDS)
            .setInputData(inputData)
            .build()

        val workManager = WorkManager.getInstance(context)
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)

        // Enqueue and wait for the result
        workManager.enqueue(request).result.get()
        testDriver?.setAllConstraintsMet(request.id)
        testDriver?.setInitialDelayMet(request.id)

        // Get WorkInfo and outputData
        val workInfo = workManager.getWorkInfoById(request.id).get()
        val outputData = workInfo.outputData

        // Assert
        assertThat(workInfo.state, `is`(WorkInfo.State.SUCCEEDED))
        assertThat(outputData, `is`(inputData))
    }

    @Test
    @Throws(Exception::class)
    fun testPeriodicWork() {
        // Define inputData
        val inputData = workDataOf(KEY_1 to 1, KEY_2 to 2)

        // Create request
        val request = PeriodicWorkRequestBuilder<EchoWorker>(15, TimeUnit.MINUTES)
            .setInputData(inputData)
            .build()

        val workManager = WorkManager.getInstance(context)
        val testDriver = WorkManagerTestInitHelper.getTestDriver(context)

        // Enqueue and wait for result
        workManager.enqueue(request).result.get()
        // Tell the testing framework the period delay is met
        testDriver?.setPeriodDelayMet(request.id)

        // Get WorkInfo and outputData
        val workInfo = workManager.getWorkInfoById(request.id).get()

        // Assert
        assertThat(workInfo.state, `is`(WorkInfo.State.ENQUEUED))
    }

    companion object {
        const val KEY_1 = "KEY_1"
        const val KEY_2 = "KEY_2"
    }
}
package com.angopa.aad.workers

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.testing.TestListenableWorkerBuilder
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RefreshingMainDataWorkTest {
    private lateinit var context: Context
    private lateinit var workManager: WorkManager

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        workManager = WorkManager.getInstance(context)
    }

    @Test
    fun testRefreshTabDataWork() {
        //Get the ListableWorker
        val worker = TestListenableWorkerBuilder<SeedTabTableWorker>(context).build()

        //Start the work synchronously
        val result = worker.startWork().get()

        assertThat(result, `is`(ListenableWorker.Result.success()))
    }

    @Test
    fun testRefreshLinkDataWork() {
        //Get the ListableWorker
        val worker = TestListenableWorkerBuilder<SeedLinkTableWorker>(context).build()

        //Start the work synchronously
        val result = worker.startWork().get()

        assertThat(result, `is`(ListenableWorker.Result.success()))
    }
}
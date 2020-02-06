package com.angopa.aad.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.angopa.aad.data.localdata.AppDatabase
import com.angopa.aad.data.localdata.Link
import com.angopa.aad.data.localdata.LinkDao
import com.angopa.aad.utilities.getValue
import com.angopa.aad.utilities.testLink
import com.angopa.aad.utilities.testTab
import com.angopa.aad.utilities.testTabs
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LinkDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var linkDao: LinkDao
    private var testTabId: Long = 0

    @get:Rule
    var instantTaskExecutionRule = InstantTaskExecutorRule()

    @Before
    fun createDb() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        linkDao = database.linkDao()

        database.tabDao().insert(testTab)
        database.tabDao().insert(testTabs[1])
        testTabId = linkDao.insertLink(testLink)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetLinks() = runBlocking {
        val link2 = Link(
            testTabs[1].tabId,
            "topic2",
            "description2"
        ).also { it.linkId = 2 }
        val link3 = Link(
            testTabs[1].tabId,
            "topic2",
            "description2"
        ).also { it.linkId = 2 }
        linkDao.insertLink(link2)
        linkDao.insertAll(arrayListOf(link3, link3, link3))
        assertThat(getValue(linkDao.getLinks()).size, equalTo(2))
    }

    @Test
    fun testGetLinksForTab() {
        assertThat(getValue(linkDao.getLinksForTab(testTab.tabId)).size, equalTo(1))
    }
}
package com.angopa.aad.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.angopa.aad.data.localdata.AppDatabase
import com.angopa.aad.data.localdata.model.Tab
import com.angopa.aad.data.localdata.TabDao
import com.angopa.aad.utilities.getValue
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TabDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var tabDao: TabDao
    private val tabA =
        Tab("1", "description")
    private val tabB =
        Tab("2", "description")
    private val tabC =
        Tab("3", "description")

    @get:Rule
    val instantTaskExecutionRule = InstantTaskExecutorRule()

    @Before
    fun createDao() = runBlocking {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        tabDao = database.tabDao()

        //Insert tab in non-alphabetical order to test that result are sorted by name
        tabDao.insertAll(listOf(tabA, tabB, tabC))
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun testGetTabs() {
        val tabList = getValue(tabDao.getTabs())
        assertThat(tabList.size, equalTo(3))
    }

    @Test
    fun testGetTab() {
        assertThat(getValue(tabDao.getTab(tabA.tabId)), equalTo(tabA))
    }
}
package com.angopa.aad.data.localdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.angopa.aad.data.localdata.model.Tab

@Dao
interface TabDao {
    @Query("SELECT * FROM tab")
    fun getTabs(): LiveData<List<Tab>>

    @Query("SELECT * FROM tab WHERE id = :tabId")
    fun getTab(tabId: String): LiveData<Tab>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(tabs: List<Tab>)

    @Insert(onConflict = REPLACE)
    suspend fun insert(tab: Tab)
}
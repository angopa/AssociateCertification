package com.angopa.aad.data.localdata

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.angopa.aad.data.localdata.Link

@Dao
interface LinkDao {
    @Query("SELECT * FROM link")
    fun getLinks(): LiveData<List<Link>>

    @Query("SELECT * FROM link WHERE tab_id = :tabId")
    fun getLinksForTab(tabId: String): LiveData<List<Link>>

    @Insert(onConflict = REPLACE)
    suspend fun insertAll(links: List<Link>)

    @Insert(onConflict = REPLACE)
    suspend fun insertLink(link: Link): Long
}
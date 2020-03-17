package com.angopa.aad.data.localdata

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.angopa.aad.data.localdata.model.Cheese

/**
 *  Created by Andres Gonzalez on 02/18/2020.
 */
@Dao
interface CheeseDao {
    /**
     * Room knows how to return a LivePagedListProvider, from which we can get a LiveData and serve
     * it back to UI via ViewModel.
     */
    @Query("SELECT * FROM cheese ORDER BY name")
    fun allCheesesByName(): DataSource.Factory<Int, Cheese>

    @Insert
    suspend fun insert(cheeses: List<Cheese>)

    @Insert
    suspend fun insert(cheese: Cheese)

    @Delete
    suspend fun delete(cheese: Cheese)
}
package com.angopa.aad.data.localdata

import androidx.paging.DataSource
import com.angopa.aad.data.localdata.model.Cheese

/**
 *  Created by Andres Gonzalez on 02/18/2020.
 */
class CheeseRepository private constructor(private val cheeseDao: CheeseDao) {

    fun getCheesesByName(): DataSource.Factory<Int, Cheese> = cheeseDao.allCheesesByName()

    suspend fun insertNewCheese(cheese: Cheese) {
        cheeseDao.insert(cheese)
    }

    suspend fun deleteCheese(cheese: Cheese) {
        cheeseDao.delete(cheese)
    }

    companion object {
        //For singleton implementation
        @Volatile
        private var instance: CheeseRepository? = null

        fun getInstance(cheeseDao: CheeseDao) = instance
            ?: synchronized(this) {
                instance
                    ?: CheeseRepository(cheeseDao).also { instance = it }
            }
    }
}
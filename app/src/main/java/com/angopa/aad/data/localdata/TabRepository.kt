package com.angopa.aad.data.localdata

/**
 * Repository module for holding data operations
 */
class TabRepository private constructor(private val tabDao: TabDao) {

    fun getTabs() = tabDao.getTabs()

    fun getTab(tabId: String) = tabDao.getTab(tabId)

    companion object {
        //For singleton implementation
        @Volatile
        private var instance: TabRepository? = null

        fun getInstance(tabDao: TabDao) = instance
            ?: synchronized(this) {
            instance
                ?: TabRepository(tabDao).also { instance = it }
        }
    }
}
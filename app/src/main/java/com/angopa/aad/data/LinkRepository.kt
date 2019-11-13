package com.angopa.aad.data

/**
 * Repository module for holding data operations
 */
class LinkRepository private constructor(private val linkDao: LinkDao) {

    fun getLinksForTab(tabId: String) = linkDao.getLinksForTab(tabId)

    companion object {
        //For singleton implementation
        @Volatile
        private var instance: LinkRepository? = null

        fun getInstance(linkDao: LinkDao) = instance ?: synchronized(this) {
            instance ?: LinkRepository(linkDao).also { instance = it }
        }
    }
}
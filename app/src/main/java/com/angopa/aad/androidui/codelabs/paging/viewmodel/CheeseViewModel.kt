package com.angopa.aad.androidui.codelabs.paging.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Config
import androidx.paging.toLiveData
import com.angopa.aad.data.localdata.Cheese
import com.angopa.aad.data.localdata.CheeseRepository
import kotlinx.coroutines.launch

/**
 *  Created by Andres Gonzalez on 02/18/2020.
 *
 *  A simple ViewModel that provides a paged list of delicious Cheeses.
 *
 */
class CheeseViewModel(
    app: Application,
    private val cheeseRepository: CheeseRepository
) : AndroidViewModel(app) {

    /**
     * We use -ktx Kotlin extension functions here, otherwise you would use LivePagedListBuilder(),
     * and PagedList.Config.Builder()
     */
    val allCheeses = cheeseRepository.getCheesesByName().toLiveData(
        Config(
            /**
             * A good page size is a value that fills at least a screen worth of content on a large
             * device so the User is unlikely to see a null item.
             * You can play with this constant to observe the paging behavior.
             * <p>
             * It's possible to vary this with list device size, but often unnecessary, unless a
             * user scrolling on a large device is expected to scroll through items more quickly
             * than a small device, such as when the large device uses a grid layout of items.
             */
            pageSize = 60,
            /**
             * If placeholders are enabled, PagedList will report the full size but some items might
             * be null in onBind method (PagedListAdapter triggers a rebind when data is loaded).
             * <p>
             * If placeholders are disabled, onBind will never receive null but as more pages are
             * loaded, the scrollbars will jitter as new pages are loaded. You should probably
             * disable scrollbars if you disable placeholders.
             */
            enablePlaceholders = true,
            /**
             * Maximum number of items a PagedList should hold in memory at once.
             * <p>
             * This number triggers the PagedList to start dropping distant pages as more are loaded.
             */
            maxSize = 200
        )
    )

    fun insert(text: CharSequence) {
        viewModelScope.launch {
            cheeseRepository.insertNewCheese(Cheese(id = 0, name = text.toString()))
        }
    }

    fun remove(cheese: Cheese) {
        viewModelScope.launch {
            cheeseRepository.deleteCheese(cheese)
        }
    }
}
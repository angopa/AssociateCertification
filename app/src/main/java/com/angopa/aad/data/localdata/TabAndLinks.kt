package com.angopa.aad.data.localdata

import androidx.room.Embedded
import androidx.room.Relation
import com.angopa.aad.data.localdata.Link
import com.angopa.aad.data.localdata.Tab

/**
 * This class capture the relation between a [Tab] and tab's [Link], which
 * is used by Room to fetch the related entities
 */
data class TabAndLinks(
    @Embedded
    val tab: Tab,

    @Relation(parentColumn = "id", entityColumn = "tab_id")
    val tabLinks: List<Link> = emptyList()
)
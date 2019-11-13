package com.angopa.aad.utilities

import com.angopa.aad.data.Link
import com.angopa.aad.data.Tab

/**
 * [Tab] objects used for testing
 */
val testTabs = arrayListOf(
    Tab("tab1", "description"),
    Tab("tab2", "description"),
    Tab("tab3", "description")
)

val testTab = testTabs[0]

/**
 * [Link] object used for test
 */
val testLink = Link(testTab.tabId, "topic", "link")
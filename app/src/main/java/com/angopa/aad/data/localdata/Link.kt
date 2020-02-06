package com.angopa.aad.data.localdata

import androidx.room.*

/**
 * [Link] represent the links contained in each [Tab]
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would no change
 */
@Entity(
    tableName = "link",
    foreignKeys = [
    ForeignKey(entity = Tab::class, parentColumns = ["id"], childColumns = ["tab_id"])
    ],
    indices = [Index("tab_id")]
)
data class Link(
    @ColumnInfo(name = "tab_id") val tabId: String,

    /**
     * Provide detail about the topic need to check
     */
    @ColumnInfo(name = "topic") val topic: String,

    /**
     * Contain the link for the current Topic, usually pointing to developer.android.com
     */
    @ColumnInfo(name = "link_uri") val linkUri: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var linkId: Long = 0
}
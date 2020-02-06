package com.angopa.aad.data.localdata

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tab")
data class Tab(
    @PrimaryKey @ColumnInfo(name = "id") val tabId: String,
    val description: String
) {
    override fun toString(): String = description
}
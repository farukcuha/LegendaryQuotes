package com.pandorina.legendaryquotes.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val QUOTE_TABLE_NAME = "quote_table"

@Entity(tableName = QUOTE_TABLE_NAME)
data class Quote(
    val text: String,
    val owner: String,
    @PrimaryKey
    var id: Long,
    val isFavorite: Boolean = false
)

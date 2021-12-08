package com.pandorina.legendaryquotes.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pandorina.legendaryquotes.model.Quote

@Database(entities = [Quote::class], version = 2, exportSchema = false)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun dao(): QuoteDao
}
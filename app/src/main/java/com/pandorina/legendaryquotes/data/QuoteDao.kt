package com.pandorina.legendaryquotes.data

import androidx.room.*
import com.pandorina.legendaryquotes.model.Quote
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: Quote)

    @Delete
    suspend fun deleteQuote(quote: Quote)

    @Query("SELECT * FROM quote_table ORDER BY id DESC")
    fun getQuotes(): Flow<List<Quote>>
}
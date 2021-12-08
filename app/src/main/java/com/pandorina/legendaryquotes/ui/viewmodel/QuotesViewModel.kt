package com.pandorina.legendaryquotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.pandorina.legendaryquotes.data.Repository
import com.pandorina.legendaryquotes.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val quoteList = liveData { repository.getQuotes().collectLatest { emit(it) } }
    val favoriteQuoteList = liveData { repository.getFavoriteQuotes().collectLatest { emit(it) } }

    fun insertQuote(quote: Quote) {
        viewModelScope.launch { repository.insertQuote(quote) }
    }

    fun deleteQuote(quote: Quote) {
        viewModelScope.launch { repository.deleteQuote(quote) }
    }

    fun deleteAll(){
        viewModelScope.launch { repository.deleteAll() }
    }

}
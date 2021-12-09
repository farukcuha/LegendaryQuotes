package com.pandorina.legendaryquotes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.legendaryquotes.databinding.ItemQuoteBinding
import com.pandorina.legendaryquotes.model.Quote
import com.pandorina.legendaryquotes.util.Util

class FavoriteQuotesAdapter :
    ListAdapter<Quote, FavoriteQuotesAdapter.FavoriteQuoteListHolder>(Util.QuoteComparator) {
    class FavoriteQuoteListHolder(private val binding: ItemQuoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Quote) {
            binding.apply {
                tvQuote.text = item.text
                tvOwner.text = item.owner
                ibEdit.isVisible = false
                ibDelete.isVisible = false
                ibFavorite.isVisible = false
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteQuoteListHolder {
        val binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteQuoteListHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteQuoteListHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
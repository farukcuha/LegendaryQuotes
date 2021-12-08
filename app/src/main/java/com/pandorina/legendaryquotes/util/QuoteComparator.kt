package com.pandorina.legendaryquotes.util

import androidx.recyclerview.widget.DiffUtil
import com.pandorina.legendaryquotes.model.Quote

object QuoteComparator : DiffUtil.ItemCallback<Quote>() {
    override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
        return oldItem == newItem
    }
}
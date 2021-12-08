package com.pandorina.legendaryquotes.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.legendaryquotes.databinding.ItemScrollableQuoteBinding
import com.pandorina.legendaryquotes.model.Quote

class ScrollableQuotesAdapter(private val list: List<Quote>) :
    RecyclerView.Adapter<ScrollableQuotesAdapter.Holder>() {
    class Holder(private val binding: ItemScrollableQuoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Quote) {
            binding.apply {
                tvQuote.text = "\"" + item.text + "\""
                tvOwner.text = "- " + item.owner
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val binding =
            ItemScrollableQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = list.size
}
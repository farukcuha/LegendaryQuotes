package com.pandorina.legendaryquotes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.legendaryquotes.databinding.ItemQuoteBinding
import com.pandorina.legendaryquotes.model.Quote

class QuoteListAdapter(
    private val editQuote: (String, String, Long) -> Unit,
    private val deleteQuote: (Quote) -> Unit
) :
    ListAdapter<Quote, QuoteListAdapter.QuoteListHolder>(QuoteComparator) {

    class QuoteListHolder(val binding: ItemQuoteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Quote) {
            binding.tvQuote.text = item.text
            binding.tvOwner.text = item.owner
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuoteListHolder {
        val binding = ItemQuoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuoteListHolder(binding)
    }

    override fun onBindViewHolder(holder: QuoteListHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)

        holder.binding.ibDelete.setOnClickListener { deleteQuote.invoke(item) }
        holder.binding.ibEdit.setOnClickListener {
            editQuote.invoke(
                holder.binding.tvQuote.text.toString(),
                holder.binding.tvOwner.text.toString(),
                item.id
            )
        }
    }

    object QuoteComparator : DiffUtil.ItemCallback<Quote>() {
        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem == newItem
        }
    }
}
package com.pandorina.legendaryquotes.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pandorina.legendaryquotes.R
import com.pandorina.legendaryquotes.databinding.ItemQuoteBinding
import com.pandorina.legendaryquotes.model.Quote
import com.pandorina.legendaryquotes.util.Util

class QuoteListAdapter(
    private val editQuote: (String, String, Long) -> Unit,
    private val deleteQuote: (Quote) -> Unit,
    private val setFavorite: (Quote) -> Unit
) :
    ListAdapter<Quote, QuoteListAdapter.QuoteListHolder>(Util.QuoteComparator) {
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

        holder.binding.ibFavorite.setImageDrawable(ResourcesCompat.getDrawable(
                holder.itemView.context.resources,
                if (item.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border,null)
        )

        holder.binding.ibFavorite.setOnClickListener {
            if (item.isFavorite){
                setFavorite.invoke(Quote(item.text, item.owner, item.id, false))
            } else {
                setFavorite.invoke(Quote(item.text, item.owner, item.id, true))
            }
        }
    }
}
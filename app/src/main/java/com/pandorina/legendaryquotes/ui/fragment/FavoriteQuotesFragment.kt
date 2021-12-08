package com.pandorina.legendaryquotes.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.legendaryquotes.R
import com.pandorina.legendaryquotes.databinding.FragmentFavoriteQuotesBinding
import com.pandorina.legendaryquotes.ui.adapter.FavoriteQuotesAdapter
import com.pandorina.legendaryquotes.ui.viewmodel.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteQuotesFragment : BaseFragment<FragmentFavoriteQuotesBinding>(FragmentFavoriteQuotesBinding::inflate){
    private val favoriteQuotesAdapter = FavoriteQuotesAdapter()
    private val quotesViewModel: QuotesViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.favorites)
            setDisplayHomeAsUpEnabled(true)
        }

        binding.root.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteQuotesAdapter
        }

        quotesViewModel.favoriteQuoteList.observe(viewLifecycleOwner){
            favoriteQuotesAdapter.submitList(it)
        }
    }

}
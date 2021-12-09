package com.pandorina.legendaryquotes.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pandorina.legendaryquotes.R
import com.pandorina.legendaryquotes.databinding.FragmentFavoriteQuotesBinding
import com.pandorina.legendaryquotes.ui.activity.GiftForKekodActivity
import com.pandorina.legendaryquotes.ui.adapter.FavoriteQuotesAdapter
import com.pandorina.legendaryquotes.ui.viewmodel.QuotesViewModel
import com.pandorina.legendaryquotes.util.Util.configureActionBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteQuotesFragment : BaseFragment<FragmentFavoriteQuotesBinding>(FragmentFavoriteQuotesBinding::inflate){
    private val favoriteQuotesAdapter = FavoriteQuotesAdapter()
    private val quotesViewModel: QuotesViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.configureActionBar(this,
            getString(R.string.favorites),
            setDisplayBackButton = true,
            setHasOptionsMenu = true
        )

        binding.root.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoriteQuotesAdapter
        }

        quotesViewModel.favoriteQuoteList.observe(viewLifecycleOwner){
            favoriteQuotesAdapter.submitList(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_favorite, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_gift_to_kekod -> {
                val intent = Intent(requireContext(), GiftForKekodActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }
}
package com.pandorina.legendaryquotes.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.pandorina.legendaryquotes.R
import com.pandorina.legendaryquotes.databinding.FragmentQuoteListBinding
import com.pandorina.legendaryquotes.databinding.ItemEditQuoteBinding
import com.pandorina.legendaryquotes.model.Quote
import com.pandorina.legendaryquotes.ui.adapter.QuoteListAdapter
import com.pandorina.legendaryquotes.ui.viewmodel.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuoteListFragment :
    BaseFragment<FragmentQuoteListBinding>(FragmentQuoteListBinding::inflate) {
    private val quotesViewModel: QuotesViewModel by viewModels()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_quote_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_clear -> {
                quotesViewModel.deleteAll()
                true
            }
            R.id.action_show_favorites -> {
                val action = QuoteListFragmentDirections.actionNavQuoteListFragmentToFavoriteQuotesFragment()
                findNavController().navigate(action)
                true
            }
            else -> false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.edit_quotes)
            setDisplayHomeAsUpEnabled(true)
        }

        setHasOptionsMenu(true)

        val quoteAddingBottomSheet = BottomSheetDialog(requireContext())
        val editQuote: (String?, String?, Long?) -> Unit = { quote, owner, id ->
            val itemEditQuoteBinding =
                ItemEditQuoteBinding.inflate(LayoutInflater.from(requireContext()))

            if (quote != null && owner != null) {
                itemEditQuoteBinding.etQuote.setText(quote)
                itemEditQuoteBinding.etGuoteOwner.setText(owner)
            }

            quoteAddingBottomSheet.setContentView(itemEditQuoteBinding.root)
            quoteAddingBottomSheet.show()

            itemEditQuoteBinding.btnSubmitQuote.setOnClickListener {
                val txtQuote = itemEditQuoteBinding.etQuote.text.toString()
                val txtOwner = itemEditQuoteBinding.etGuoteOwner.text.toString()

                if (TextUtils.isEmpty(txtQuote) || TextUtils.isEmpty(txtOwner)) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.toast_message),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    quotesViewModel.insertQuote(
                        Quote(
                            txtQuote,
                            txtOwner,
                            id ?: System.currentTimeMillis()
                        )
                    )
                }
                quoteAddingBottomSheet.dismiss()
                itemEditQuoteBinding.apply {
                    etQuote.text?.clear()
                    etGuoteOwner.text?.clear()
                    etQuote.clearFocus()
                    etGuoteOwner.clearFocus()
                }
            }
        }
        val deleteQuote: (Quote) -> Unit = { quotesViewModel.deleteQuote(it) }

        val addToFavorite: (Quote) -> Unit = { quotesViewModel.insertQuote(it) }

        val removeFromFavorite: (Quote) -> Unit = { quotesViewModel.insertQuote(it) }

        val quoteListAdapter = QuoteListAdapter(editQuote, deleteQuote, addToFavorite, removeFromFavorite)

        binding.btnAddQuote.setOnClickListener {
            quoteAddingBottomSheet.show()
            editQuote.invoke(null, null, null)
        }

        binding.rvQuoteList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = quoteListAdapter
            setHasFixedSize(true)
        }

        quotesViewModel.quoteList.observe(viewLifecycleOwner){
            quoteListAdapter.submitList(it)
        }

    }
}
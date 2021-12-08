package com.pandorina.legendaryquotes.ui.fragment

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.pandorina.legendaryquotes.R
import com.pandorina.legendaryquotes.data.Resources
import com.pandorina.legendaryquotes.databinding.FragmentScrollableQuoteBinding
import com.pandorina.legendaryquotes.model.Quote
import com.pandorina.legendaryquotes.ui.adapter.ScrollableQuotesAdapter
import com.pandorina.legendaryquotes.ui.viewmodel.DataStoreViewModel
import com.pandorina.legendaryquotes.ui.viewmodel.QuoteListViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScrollableQuoteFragment :
    BaseFragment<FragmentScrollableQuoteBinding>(FragmentScrollableQuoteBinding::inflate) {
    private val quotesViewModel: QuoteListViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    var list = listOf<Quote>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionBarConfiguration()
        startImageChangingButtonAnim()
        initViewPager()
        observeBackgroundImage()
        onClickImageChangeButton()

        binding.lottieArrowDown.setOnClickListener { swipeViewPager(false) }
        binding.lottieArrowUp.setOnClickListener { swipeViewPager(true) }
    }

    private fun onClickImageChangeButton() {
        binding.ivChangeBg.setOnClickListener {
            dataStoreViewModel.changeBackgroundImage(Resources.backgroundList.random())
        }
    }

    private fun observeBackgroundImage() {
        dataStoreViewModel.getBackgroundImage.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it)
                .centerCrop()
                .into(binding.ivChangeBg)
        }
    }

    @SuppressLint("SetTextI18n")
    fun displayPagePosition(position: Int, list: List<Quote>) {
        binding.tvPagePosition.text = "${position + 1}/${list.size}"
    }

    private fun initViewPager() {
        binding.viewPagerQuotes.apply {
            quotesViewModel.quoteList.observe(viewLifecycleOwner) {
                list = it
                adapter = ScrollableQuotesAdapter(list)
                displayPagePosition(binding.viewPagerQuotes.currentItem, list)
            }
        }

        binding.viewPagerQuotes.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                displayPagePosition(position, list)
                if (list.isEmpty() || list.size == 1) {
                    binding.lottieArrowDown.isVisible = false
                    binding.lottieArrowUp.isVisible = false
                } else {
                    when (position) {
                        list.size - 1 -> {
                            binding.lottieArrowDown.isVisible = false
                            binding.lottieArrowUp.isVisible = true
                        }
                        0 -> {
                            binding.lottieArrowUp.isVisible = false
                            binding.lottieArrowDown.isVisible = true
                        }
                        else -> {
                            binding.lottieArrowDown.isVisible = true
                            binding.lottieArrowUp.isVisible = true
                        }
                    }
                }
            }
        })
    }

    private fun setActionBarConfiguration() {
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.legendary_quotes)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_quote_list -> {
                val action =
                    ScrollableQuoteFragmentDirections.actionScrollableQuoteFragmentToQuoteListFragment()
                findNavController().navigate(action)
            }
            R.id.action_share -> {
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "text/plain"
                val sharingQuoteItem = list[binding.viewPagerQuotes.currentItem]
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "${sharingQuoteItem.text} \n - ${sharingQuoteItem.owner}"
                )
                startActivity(shareIntent)
            }
        }
        return true
    }

    private fun startImageChangingButtonAnim() {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1.2f)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1.2f)
        val anim = ObjectAnimator.ofPropertyValuesHolder(binding.ivChangeBg, scaleX, scaleY)
        anim.repeatCount = ValueAnimator.INFINITE
        anim.repeatMode = ValueAnimator.REVERSE
        anim.duration = 1000
        anim.start()
    }

    private fun swipeViewPager(isUp: Boolean) {
        val currentViewPagerPosition = binding.viewPagerQuotes.currentItem
        binding.viewPagerQuotes.setCurrentItem(
            if (isUp) currentViewPagerPosition - 1
            else currentViewPagerPosition + 1, true
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.menu_scrollable_quotes, menu)
    }
}
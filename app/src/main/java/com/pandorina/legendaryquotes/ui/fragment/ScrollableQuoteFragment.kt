package com.pandorina.legendaryquotes.ui.fragment

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.res.ResourcesCompat
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
import com.pandorina.legendaryquotes.ui.viewmodel.QuotesViewModel
import com.pandorina.legendaryquotes.util.Util.configureActionBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScrollableQuoteFragment :
    BaseFragment<FragmentScrollableQuoteBinding>(FragmentScrollableQuoteBinding::inflate) {
    private val quotesViewModel: QuotesViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private var list = listOf<Quote>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setActionBarConfiguration()
        initViewPager()
        observeBackgroundImage()

        startImageChangingButtonAnim(1.1f, binding.ivChangeBg)
        startImageChangingButtonAnim(1.05f, binding.bgChangeImage)

        binding.lottieArrowDown.setOnClickListener { swipeViewPager(false) }
        binding.lottieArrowUp.setOnClickListener { swipeViewPager(true) }
        binding.ivChangeBg.setOnClickListener { onClickImageChangeButton() }
    }

    private fun onClickImageChangeButton() {
        val randomBackgroundDrawable = Resources.backgroundList.random()
        dataStoreViewModel.getBackgroundImage.value?.let {
            if (it == randomBackgroundDrawable) {
                onClickImageChangeButton()
            } else {
                dataStoreViewModel.changeBackgroundImage(randomBackgroundDrawable)
            }
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

    private fun initViewPager() {
        binding.viewPagerQuotes.apply {
            quotesViewModel.quoteList.observe(viewLifecycleOwner) {
                list = it
                adapter = ScrollableQuotesAdapter(list)
                checkViewPagerState(0)
            }
        }

        binding.viewPagerQuotes.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                checkViewPagerState(position)
            }
        })
    }

    @SuppressLint("SetTextI18n")
    fun checkViewPagerState(position: Int){
        when(list.size){
            0 -> {
                binding.lottieArrowDown.isVisible = false
                binding.lottieArrowUp.isVisible = false
                binding.tvPagePosition.text = getString(R.string.page_0_0)
            }
            1 -> {
                binding.lottieArrowDown.isVisible = false
                binding.lottieArrowUp.isVisible = false
                binding.tvPagePosition.text = getString(R.string.page_1_1)
            }
            else -> {
                binding.tvPagePosition.text = "${position + 1}/${list.size}"
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
    }

    private fun setActionBarConfiguration() {
        activity?.configureActionBar(
            this,
            getString(R.string.legendary_quotes),
            setHasOptionsMenu = true
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_quote_list -> {
                val action =
                    ScrollableQuoteFragmentDirections.actionScrollableQuoteFragmentToQuoteListFragment()
                findNavController().navigate(action)
            }
        }
        return true
    }

    private fun startImageChangingButtonAnim(animValue: Float, view: View) {
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, animValue)
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, animValue)
        val anim = ObjectAnimator.ofPropertyValuesHolder(view, scaleX, scaleY)
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
package com.pandorina.legendaryquotes.ui.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.pandorina.legendaryquotes.R
import com.pandorina.legendaryquotes.data.Resources
import com.pandorina.legendaryquotes.ui.viewmodel.DataStoreViewModel
import com.pandorina.legendaryquotes.ui.viewmodel.QuotesViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val quotesViewModel: QuotesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataStoreViewModel.getBackgroundImage.observe(this) {
            findViewById<FrameLayout>(R.id.root_parent_main_activity)
                .background = ResourcesCompat.getDrawable(resources, it, null)
        }
        createDefaultQuotes()
    }

    private fun createDefaultQuotes() {
        for (quote in Resources.quoteList) {
            quotesViewModel.insertQuote(quote)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return false
    }
}
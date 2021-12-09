package com.pandorina.legendaryquotes.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.pandorina.legendaryquotes.R
import com.pandorina.legendaryquotes.data.Resources
import com.pandorina.legendaryquotes.databinding.ActivityMainBinding
import com.pandorina.legendaryquotes.ui.viewmodel.DataStoreViewModel
import com.pandorina.legendaryquotes.ui.viewmodel.QuotesViewModel
import com.pandorina.legendaryquotes.util.Util
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val quotesViewModel: QuotesViewModel by viewModels()

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreViewModel.getBackgroundImage.observe(this) {
            binding.rootParentMainActivity
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
            R.id.action_share -> {
                val screenShotUri = Util.saveMediaToStorage(Util
                    .getScreenShotFromView(binding.rootParentMainActivity), this)
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "image/jpeg"
                shareIntent.putExtra(Intent.EXTRA_STREAM, screenShotUri)
                startActivity(shareIntent)
            }
        }
        return false
    }
}
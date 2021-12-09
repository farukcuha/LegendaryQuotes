package com.pandorina.legendaryquotes.ui.activity

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.pandorina.legendaryquotes.databinding.ActivityGiftForKekodBinding

class GiftForKekodActivity : AppCompatActivity() {
    lateinit var binding: ActivityGiftForKekodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGiftForKekodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.animatedSvgViewForKekod.start()
        Handler(mainLooper).postDelayed({
            finish()
        }, 10000)
    }
}
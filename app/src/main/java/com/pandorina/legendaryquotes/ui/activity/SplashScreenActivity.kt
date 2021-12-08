package com.pandorina.legendaryquotes.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pandorina.legendaryquotes.R

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(mainLooper).postDelayed({
            Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
        }, 3000)

        findViewById<ImageView>(R.id.iv_app_icon_foreground)
            .startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in))
        findViewById<TextView>(R.id.tv_signature)
            .startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))


    }
}
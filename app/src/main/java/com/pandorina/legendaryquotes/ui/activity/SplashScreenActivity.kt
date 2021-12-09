package com.pandorina.legendaryquotes.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jaredrummler.android.widget.AnimatedSvgView
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
        }, 5000)

        findViewById<AnimatedSvgView>(R.id.animated_svg_view).start()
        findViewById<TextView>(R.id.tv_signature)
            .startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_up))
    }
}
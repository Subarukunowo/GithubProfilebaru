package com.sandy.githubprofile.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.sandy.githubprofile.databinding.ActivitySplashScreenBinding
import com.sandy.githubprofile.helpers.Config

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menggunakan binding untuk layout SplashScreen
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Lottie Animation splash screen
        val splashAnimation: LottieAnimationView = binding.animationView
        splashAnimation.playAnimation()

        // Delay sebelum melanjutkan ke MainActivity
        Handler(mainLooper).postDelayed({
            // Memulai MainActivity setelah animasi selesai
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()  // Menutup SplashScreenActivity agar tidak bisa kembali
        }, Config.SPLASH_SCREEN_DELAY)  // Menggunakan konfigurasi delay
    }
}

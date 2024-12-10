package com.sandy.githubprofile.helpers

import com.sandy.githubprofile.BuildConfig
class
Config {
    companion object {
        const val SPLASH_SCREEN_DELAY: Long = 3000
        const val BASE_URL = "https://api.github.com"
        const val DEFAULT_USER_LOGIN = "Subarukunowo"

        // Menggunakan val untuk token yang diambil dari BuildConfig aplikasi Anda
        val PERSONAL_ACCESS_TOKEN = "token ${BuildConfig.ACCESS_TOKEN}"
    }
}

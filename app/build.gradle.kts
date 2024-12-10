import java.util.Properties
import java.io.FileInputStream
import org.gradle.api.GradleException

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

val apikeyPropertiesFile = rootProject.file("apikey.properties")
val apikeyProperties = Properties()

// Memuat file apikey.properties jika ada
if (apikeyPropertiesFile.exists()) {
    apikeyProperties.load(FileInputStream(apikeyPropertiesFile))
} else {
    throw GradleException("File apikey.properties tidak ditemukan di root project.")
}

// Ambil nilai ACCESS_TOKEN dari file apikey.properties
val accessToken = apikeyProperties.getProperty("ACCESS_TOKEN", "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")

android {
    namespace = "com.sandy.githubprofile"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sandy.githubprofile"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // Menambahkan ACCESS_TOKEN ke dalam BuildConfig aplikasi Anda
        buildConfigField("String", "ACCESS_TOKEN", "\"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\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation("com.airbnb.android:lottie:5.2.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    kapt("com.github.bumptech.glide:compiler:4.15.1")
    implementation("com.github.bumptech.glide:glide:4.15.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.9")

    // Dependencies lainnya
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17) // Ganti ke JDK 17
    }
}

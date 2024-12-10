package com.sandy.githubprofile.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sandy.githubprofile.R
import com.sandy.githubprofile.databinding.ActivityMainBinding
import com.sandy.githubprofile.helpers.Config
import com.sandy.githubprofile.models.GithubUser
import com.sandy.githubprofile.services.GithubUserService
import com.sandy.githubprofile.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge support
        enableEdgeToEdge()

        // Inflate layout for MainActivity
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hide action bar
        supportActionBar?.hide()

        // Handle system padding (status bar and navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set button click listener to search user by login
        binding.btnSearchUserLogin.setOnClickListener {
            val userLogin = binding.etSearchUserLogin.text.toString()
            if (userLogin.isNotEmpty()) {
                searchUser(userLogin)
            } else {
                binding.tvUser.text = "Please enter a valid username"
                Glide.with(applicationContext)
                    .load(R.drawable.baseline_broken_image_24)
                    .into(binding.imgUser)
            }
        }

        // Default search user login
        searchUser(Config.DEFAULT_USER_LOGIN)
    }

    private fun searchUser(query: String) {
        showLoading(true)
        Log.d(TAG, "Searching user: $query...")

        val githubUserService: GithubUserService = ServiceBuilder.buildService(GithubUserService::class.java)
        val requestCall: Call<GithubUser> = githubUserService.loginUser(Config.PERSONAL_ACCESS_TOKEN, query)

        Log.d(TAG, "Request URL: ${requestCall.request().url}")

        requestCall.enqueue(object : Callback<GithubUser> {
            override fun onResponse(call: Call<GithubUser>, response: Response<GithubUser>) {
                showLoading(false)//
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result != null) {
                        setUserData(result)
                    }
                    Log.d(TAG, "User data fetched successfully.")
                } else {
                    binding.tvUser.text = "User Not Found"
                    Glide.with(applicationContext)
                        .load(R.drawable.baseline_broken_image_24)
                        .into(binding.imgUser)
                    Log.d(TAG, "User not found or error in response.")
                }
            }

            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "Error fetching user data", t)
                binding.tvUser.text = "Error occurred while fetching user data"
                Glide.with(applicationContext)
                    .load(R.drawable.baseline_broken_image_24)
                    .into(binding.imgUser)
            }
        })
    }

    private fun setUserData(githubUser: GithubUser) {
        binding.tvUser.text = githubUser.toString()
        Glide.with(applicationContext)
            .load(githubUser.avatarUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.baseline_image_24)
                    .error(R.drawable.baseline_broken_image_24)
            )
            .into(binding.imgUser)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}

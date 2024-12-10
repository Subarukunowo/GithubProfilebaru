package com.sandy.githubprofile.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sandy.githubprofile.helpers.Config
import com.sandy.githubprofile.models.GithubUser
import com.sandy.githubprofile.services.ServiceBuilder
import com.sandy.githubprofile.services.GithubUserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModels : ViewModel() {
    companion object {
        val TAG: String = MainViewModels::class.java.simpleName
    }


    private val _githubUser = MutableLiveData<GithubUser>()
    val githubUser: LiveData<GithubUser> = _githubUser


    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    init {
        searchUser(Config.DEFAULT_USER_LOGIN)
    }


    fun searchUser(query: String) {
        _isLoading.value = true
        Log.d(TAG, "getDataUserProfileFromAPI: start...")

        val githubUserService: GithubUserService = ServiceBuilder.buildService(GithubUserService::class.java)
        val requestCall: Call<GithubUser> = githubUserService.loginUser(Config.PERSONAL_ACCESS_TOKEN, query)
        Log.d(TAG, "getDataUserFromAPI: ${requestCall.request().url}")


        requestCall.enqueue(object : Callback<GithubUser> {
            override fun onResponse(call: Call<GithubUser>, response: Response<GithubUser>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val result = response.body()
                    Log.d(TAG, "User Data: $result")
                    _githubUser.postValue(result)
                } else {
                    Log.d(TAG, "getDataUserFromAPI: onResponse failed...")
                }
            }

            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                _isLoading.value = false
                Log.d(TAG, "getDataUserFromAPI: onFailure ${t.message}...")
            }
        })
    }
}

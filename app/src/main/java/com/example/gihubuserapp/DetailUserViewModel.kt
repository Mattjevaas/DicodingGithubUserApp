package com.example.gihubuserapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gihubuserapp.data.response.GithubUserDetailResponse
import com.example.gihubuserapp.data.response.GithubUsersResponseItem
import com.example.gihubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {
    private val _githubUser = MutableLiveData<GithubUserDetailResponse>()
    val githubUser: LiveData<GithubUserDetailResponse> = _githubUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)

        client.enqueue(object: Callback<GithubUserDetailResponse> {
            override fun onResponse(
                call: Call<GithubUserDetailResponse>,
                response: Response<GithubUserDetailResponse>
            ) {
                _isLoading.value = false
                if(!response.isSuccessful) {
                    return
                }

                val responseBody = response.body() ?: return
                _githubUser.value = responseBody
            }

            override fun onFailure(call: Call<GithubUserDetailResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}
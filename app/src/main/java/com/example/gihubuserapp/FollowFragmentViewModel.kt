package com.example.gihubuserapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gihubuserapp.data.response.GithubUsersResponseItem
import com.example.gihubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowFragmentViewModel: ViewModel() {
    private val _githubUsers = MutableLiveData<List<GithubUsersResponseItem>?>()
    val githubUsers: LiveData<List<GithubUsersResponseItem>?> = _githubUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getFollowers(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUserFollowers(username)

        client.enqueue(object: Callback<List<GithubUsersResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubUsersResponseItem>>,
                response: Response<List<GithubUsersResponseItem>>
            ) {
                _isLoading.value = false

                val responseBody = response.body() ?: return

                _githubUsers.value = responseBody
            }

            override fun onFailure(call: Call<List<GithubUsersResponseItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun getFollowing(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUserFollowing(username)

        client.enqueue(object: Callback<List<GithubUsersResponseItem>> {
            override fun onResponse(
                call: Call<List<GithubUsersResponseItem>>,
                response: Response<List<GithubUsersResponseItem>>
            ) {
                _isLoading.value = false

                val responseBody = response.body() ?: return

                _githubUsers.value = responseBody
            }

            override fun onFailure(call: Call<List<GithubUsersResponseItem>>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }
}
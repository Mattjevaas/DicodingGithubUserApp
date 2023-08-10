package com.example.gihubuserapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gihubuserapp.data.response.GithubUsersResponseItem
import com.example.gihubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    private val _githubUsers = MutableLiveData<List<GithubUsersResponseItem>?>()
    val githubUsers: LiveData<List<GithubUsersResponseItem>?> = _githubUsers

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    init {
        getUsers()
    }

    private fun getUsers() {
        _isLoading.value = true
        _isError.value = false

        val client = ApiConfig.getApiService().getUsers()

        client.enqueue(object: Callback<List<GithubUsersResponseItem>>{
            override fun onResponse(
                call: Call<List<GithubUsersResponseItem>>,
                response: Response<List<GithubUsersResponseItem>>
            ) {
                _isLoading.value = false
                if(!response.isSuccessful) {
                    _isError.value = true
                    return
                }

                val responseBody = response.body()
                if(responseBody == null) {
                    _isError.value = true
                    return
                }

                _githubUsers.value = responseBody
            }

            override fun onFailure(call: Call<List<GithubUsersResponseItem>>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }
}
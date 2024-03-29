package com.example.gihubuserapp.data.remote.retrofit

import com.example.gihubuserapp.BuildConfig
import com.example.gihubuserapp.data.remote.response.GithubUserDetailResponse
import com.example.gihubuserapp.data.remote.response.GithubUserSearchResponse
import com.example.gihubuserapp.data.remote.response.GithubUsersResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object {
        const val TOKEN: String = BuildConfig.API_KEY
    }

    @GET("users")
    @Headers(
        "Authorization: Bearer $TOKEN",
        "Accept: application/json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    fun getUsers(): Call<List<GithubUsersResponseItem>>

    @GET("search/users")
    @Headers(
        "Authorization: Bearer $TOKEN",
        "Accept: application/json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    fun searchUser(@Query("q") name: String): Call<GithubUserSearchResponse>

    @GET("/users/{username}")
    @Headers(
        "Authorization: Bearer $TOKEN",
        "Accept: application/json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    fun getUserDetail(@Path("username") username: String): Call<GithubUserDetailResponse>

    @GET("/users/{username}/following")
    @Headers(
        "Authorization: Bearer $TOKEN",
        "Accept: application/json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    fun getUserFollowing(@Path("username") username: String): Call<List<GithubUsersResponseItem>>

    @GET("/users/{username}/followers")
    @Headers(
        "Authorization: Bearer $TOKEN",
        "Accept: application/json",
        "X-GitHub-Api-Version: 2022-11-28"
    )
    fun getUserFollowers(@Path("username") username: String): Call<List<GithubUsersResponseItem>>
}
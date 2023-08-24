package com.example.gihubuserapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.gihubuserapp.data.local.entity.GithubUserEntity


@Dao
interface FavoriteUserDao {

    @Query("SELECT * FROM FavoriteUsers")
    fun getUsers(): LiveData<List<GithubUserEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun saveUser(githubUser: GithubUserEntity)

    @Delete
    suspend fun deleteUser(githubUser: GithubUserEntity)

    @Query("SELECT EXISTS(SELECT * FROM FavoriteUsers WHERE username = :username)")
    fun isUserBookmarked(username: String): LiveData<Boolean>
}
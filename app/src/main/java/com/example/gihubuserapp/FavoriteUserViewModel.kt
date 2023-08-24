package com.example.gihubuserapp

import androidx.lifecycle.*
import com.example.gihubuserapp.data.Result
import com.example.gihubuserapp.data.local.entity.GithubUserEntity
import com.example.gihubuserapp.data.local.room.FavoriteUserDao
import kotlinx.coroutines.launch

class FavoriteUserViewModel(private val favoriteUserDao: FavoriteUserDao) : ViewModel() {

    fun getUsers(): LiveData<Result<List<GithubUserEntity>>> = liveData {
        emit(Result.Loading)
        val result: LiveData<Result<List<GithubUserEntity>>> =
            favoriteUserDao.getUsers().map { Result.Success(it) }
        emitSource(result)
    }

    fun isUserBookmarked(username: String): LiveData<Boolean> = liveData {
        val result = favoriteUserDao.isUserBookmarked(username)
        emitSource(result)
    }

    fun saveUser(githubUser: GithubUserEntity) {
        viewModelScope.launch {
            favoriteUserDao.saveUser(githubUser)
        }
    }

    fun deleteUser(githubUser: GithubUserEntity) {
        viewModelScope.launch {
            favoriteUserDao.deleteUser(githubUser)
        }
    }
}
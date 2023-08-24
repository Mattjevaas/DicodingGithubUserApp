package com.example.gihubuserapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.gihubuserapp.data.Result
import com.example.gihubuserapp.data.local.entity.GithubUserEntity
import com.example.gihubuserapp.data.local.room.FavoriteUserDao
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

internal class FavoriteUserViewModelTest {

    private lateinit var favoriteUserViewModel: FavoriteUserViewModel
    private lateinit var favoriteUserDao: FavoriteUserDao

    @Before
    fun before() {
        favoriteUserDao = mock(FavoriteUserDao::class.java)
        favoriteUserViewModel = mock(FavoriteUserViewModel(favoriteUserDao)::class.java)
    }

    @Test
    fun getUsers() {
        val dummyValue: LiveData<Result<List<GithubUserEntity>>> = liveData {
            Result.Success(
                listOf(
                    GithubUserEntity("test", "test")
                )
            )
        }

        `when`(favoriteUserViewModel.getUsers()).thenReturn(dummyValue)

        val users = favoriteUserViewModel.getUsers()
        verify(favoriteUserViewModel).getUsers()

        assertEquals(dummyValue, users)
    }

    @Test
    fun isUserBookmarked() {
        val username: String = "test"
        val dummyValue: LiveData<Boolean> = liveData { true }

        `when`(favoriteUserViewModel.isUserBookmarked(username)).thenReturn(dummyValue)

        val isBookmarked = favoriteUserViewModel.isUserBookmarked(username)
        verify(favoriteUserViewModel).isUserBookmarked(username)

        assertEquals(dummyValue, isBookmarked)
    }
}
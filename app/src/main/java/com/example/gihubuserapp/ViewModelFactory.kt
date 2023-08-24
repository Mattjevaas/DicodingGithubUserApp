package com.example.gihubuserapp

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gihubuserapp.data.local.room.FavoriteUserRoomDatabase

class ViewModelFactory(
    private val preferences: SettingPreferences,
    private val favoriteUserRoomDatabase: FavoriteUserRoomDatabase
) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SettingThemeViewModel::class.java)) {
            return SettingThemeViewModel(preferences) as T
        }

        if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            val dao = favoriteUserRoomDatabase.favoriteUserDao()
            return FavoriteUserViewModel(dao) as T
        }

        throw java.lang.IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(
            context: Context,
        ): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    SettingPreferences.getInstance(context.dataStore),
                    FavoriteUserRoomDatabase.getInstance(context)
                )
            }.also { instance = it }
    }
}
package com.example.gihubuserapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.gihubuserapp.data.local.entity.GithubUserEntity


@Database(entities = [GithubUserEntity::class], version = 1, exportSchema = false)
abstract  class FavoriteUserRoomDatabase: RoomDatabase() {
    abstract fun favoriteUserDao(): FavoriteUserDao

    companion object {
        @Volatile
        private var instance: FavoriteUserRoomDatabase? = null
        fun getInstance(context: Context): FavoriteUserRoomDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteUserRoomDatabase::class.java, "favorite_user.db"
                ).build()
            }
    }
}
package com.example.gihubuserapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class FavoriteUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_user)

        val favoriteUserViewModel = ViewModelProvider(
            this, ViewModelFactory.getInstance(
                application,
            )
        )[FavoriteUserViewModel::class.java]
    }
}
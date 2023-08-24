package com.example.gihubuserapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gihubuserapp.data.Result
import com.example.gihubuserapp.data.local.entity.GithubUserEntity
import com.example.gihubuserapp.databinding.ActivityFavoriteUserBinding
import com.example.gihubuserapp.ui.ListFavoriteAdapter

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.bookmark_page_title)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFavorite.addItemDecoration(itemDecoration)
        binding.rvFavorite.setHasFixedSize(true)

        val favoriteUserViewModel = ViewModelProvider(
            this, ViewModelFactory.getInstance(
                application,
            )
        )[FavoriteUserViewModel::class.java]

        favoriteUserViewModel.getUsers().observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    setUserData(result.data)
                }
                is Result.Loading -> {
                    setUserData(listOf(GithubUserEntity()))
                }
                else -> {
                    setUserData(listOf(GithubUserEntity()))
                }
            }
        }
    }

    private fun setUserData(githubUsers: List<GithubUserEntity>?) {
        val listFavoriteAdapter = ListFavoriteAdapter(githubUsers!!, ::goToUserDetail)
        binding.rvFavorite.adapter = listFavoriteAdapter
    }

    private fun goToUserDetail(username: String) {
        val moveIntent = Intent(this@FavoriteUserActivity, DetailUserActivity::class.java)
        moveIntent.putExtra(MainActivity.EXTRA_USERNAME, username)
        startActivity(moveIntent)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}
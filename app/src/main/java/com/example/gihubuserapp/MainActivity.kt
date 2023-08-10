package com.example.gihubuserapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gihubuserapp.data.response.GithubUsersResponseItem
import com.example.gihubuserapp.databinding.ActivityMainBinding
import com.example.gihubuserapp.ui.ListUserAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
        binding.rvUsers.setHasFixedSize(true)

        mainViewModel.githubUsers.observe(this) {githubUsers ->
            setUserData(githubUsers)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.isError.observe(this) {
            showError(it)
        }

//        mainViewModel.isEmpty.observe(this) {
//            showEmpty(it)
//        }

        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(query == null || query.isEmpty()) {
                    return false
                }

                mainViewModel.searchUser(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText == null || newText.isEmpty()) {
                    mainViewModel.refreshData()
                    return false
                }
                return false
            }
        })
    }

    private fun goToUserDetail(user: GithubUsersResponseItem){
        return
    }

    private fun setUserData(githubUsers: List<GithubUsersResponseItem>?) {
        val listUserAdapter = ListUserAdapter(githubUsers!! ,::goToUserDetail)
        //listUserAdapter.submitList(githubUsers)
        binding.rvUsers.adapter = listUserAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showError(isError: Boolean) {
        if (isError) {
            binding.errorImage.visibility = View.VISIBLE
            binding.rvUsers.visibility = View.GONE
        } else {
            binding.errorImage.visibility = View.GONE
            binding.rvUsers.visibility = View.VISIBLE
        }
    }
}
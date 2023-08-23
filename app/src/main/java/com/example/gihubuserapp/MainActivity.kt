package com.example.gihubuserapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
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

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager

        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)
        binding.rvUsers.setHasFixedSize(true)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val themeViewModel = ViewModelProvider(this, ViewModelFactory(pref))[SettingThemeViewModel::class.java]

        themeViewModel.getThemeSettings().observe(this) {isDarkModeActive: Boolean ->
            if(isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

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
                if(query.isNullOrEmpty()) {
                    return false
                }

                mainViewModel.searchUser(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText.isNullOrEmpty()) {
                    mainViewModel.refreshData()
                    return false
                }
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_view_item, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.setting_page -> {
                val intent = Intent(binding.root.context, SettingThemeActivity::class.java)
                startActivity(intent)
            }
        }

        return false
    }

    private fun goToUserDetail(user: GithubUsersResponseItem){
        val moveIntent = Intent(this@MainActivity, DetailUserActivity::class.java)
        moveIntent.putExtra(EXTRA_USERNAME, user.login)
        startActivity(moveIntent)
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

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

}
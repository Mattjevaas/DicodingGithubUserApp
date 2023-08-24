package com.example.gihubuserapp

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.gihubuserapp.data.local.entity.GithubUserEntity
import com.example.gihubuserapp.databinding.ActivityDetailUserBinding
import com.example.gihubuserapp.ui.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val userDetailViewModel: DetailUserViewModel by viewModels()
    private var isSaved: Boolean = false;
    private var userEntity: GithubUserEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(MainActivity.EXTRA_USERNAME)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (username != null) {
            supportActionBar?.title = username

            userDetailViewModel.getUserDetail(username)

            val sectionsPagerAdapter = SectionsPagerAdapter(this, username!!)
            val viewPager: ViewPager2 = binding.viewPager
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = binding.tabsUser
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            val favoriteUserViewModel = ViewModelProvider(
                this, ViewModelFactory.getInstance(
                    application
                )
            )[FavoriteUserViewModel::class.java]

            favoriteUserViewModel.isUserBookmarked(username).observe(this) { isBookmarked ->

                isSaved = isBookmarked

                if (isBookmarked) {
                    binding.fab.setImageResource(R.drawable.bookmark_icon)
                } else {
                    binding.fab.setImageResource(R.drawable.bookmark_secondary)
                }
            }

            binding.fab.setOnClickListener {

                if (userEntity == null) {
                    return@setOnClickListener
                }

                if (isSaved) {
                    favoriteUserViewModel.deleteUser(userEntity!!)
                } else {
                    favoriteUserViewModel.saveUser(userEntity!!)
                }
            }
        }

        userDetailViewModel.githubUser.observe(this) { userDetail ->
            Glide.with(this)
                .load(userDetail.avatarUrl)
                .centerCrop()
                .placeholder(R.drawable.img_placeholder_svg)
                .into(binding.userAvatar)
            binding.userFullName.text = userDetail.name
            binding.userName.text = userDetail.login
            binding.followingText.text =
                StringBuilder(userDetail.following.toString()).append(" Following")
            binding.followersText.text =
                StringBuilder(userDetail.followers.toString()).append(" Followers")

            userEntity = GithubUserEntity(userDetail.login, userDetail.avatarUrl)
        }

        userDetailViewModel.isLoading.observe(this) {
            if (it) {
                binding.loadingState.visibility = View.VISIBLE
                binding.hasDataState.visibility = View.INVISIBLE
                binding.fab.visibility = View.INVISIBLE
            } else {
                binding.loadingState.visibility = View.INVISIBLE
                binding.hasDataState.visibility = View.VISIBLE
                binding.fab.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following_tab,
            R.string.followers_tab
        )
    }
}
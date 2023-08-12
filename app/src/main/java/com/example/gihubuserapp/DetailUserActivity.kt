package com.example.gihubuserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.gihubuserapp.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityDetailUserBinding
    private val userDetailViewModel: DetailUserViewModel by viewModels()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.following_tab,
            R.string.followers_tab
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(MainActivity.EXTRA_USERNAME)

        if (username != null) {
            userDetailViewModel.getUserDetail(username)

            val sectionsPagerAdapter = SectionsPagerAdapter(this, username!!)
            val viewPager: ViewPager2 = findViewById(R.id.view_pager)
            viewPager.adapter = sectionsPagerAdapter
            val tabs: TabLayout = findViewById(R.id.tabsUser)
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        userDetailViewModel.githubUser.observe(this) { userDetail ->
            Glide.with(this)
                .load(userDetail.avatarUrl)
                .centerCrop()
                .placeholder(R.drawable.img_placeholder_svg)
                .into(binding.userAvatar)
            binding.userFullName.text = userDetail.name
            binding.userName.text = userDetail.login
            binding.followingText.text = "${userDetail.following} Following"
            binding.followersText.text = "${userDetail.followers} Followers"
        }

        userDetailViewModel.isLoading.observe(this) {
            if(it) {
                binding.loadingState.visibility = View.VISIBLE
                binding.hasDataState.visibility = View.INVISIBLE
            } else {
                binding.loadingState.visibility = View.INVISIBLE
                binding.hasDataState.visibility = View.VISIBLE
            }
        }
    }
}
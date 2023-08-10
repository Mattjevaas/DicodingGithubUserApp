package com.example.gihubuserapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gihubuserapp.R
import com.example.gihubuserapp.data.response.GithubUsersResponseItem
import com.example.gihubuserapp.databinding.ItemRowUserBinding

class ListUserAdapter: androidx.recyclerview.widget.ListAdapter<GithubUsersResponseItem, ListUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUsersResponseItem){
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .centerCrop()
                .placeholder(R.drawable.img_placeholder_svg)
                .into(binding.imgItemPhoto)
            binding.itemName.text = user.login
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUsersResponseItem>() {
            override fun areItemsTheSame(oldItem: GithubUsersResponseItem, newItem: GithubUsersResponseItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: GithubUsersResponseItem, newItem: GithubUsersResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}
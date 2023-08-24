package com.example.gihubuserapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gihubuserapp.R
import com.example.gihubuserapp.data.local.entity.GithubUserEntity
import com.example.gihubuserapp.databinding.ItemRowUserBinding
import com.example.gihubuserapp.databinding.NotFoundBinding

class ListFavoriteAdapter(
    private val users: List<GithubUserEntity>,
    private val onClick: (String) -> Unit
) : androidx.recyclerview.widget.ListAdapter<GithubUserEntity, RecyclerView.ViewHolder>(
    DIFF_CALLBACK
) {


    private inner class MyViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubUserEntity) {
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .centerCrop()
                .placeholder(R.drawable.img_placeholder_svg)
                .into(binding.imgItemPhoto)
            binding.itemName.text = user.username
            itemView.setOnClickListener {
                onClick(user.username)
            }
        }
    }

    private inner class NotFoundViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return if (viewType == VIEW_TYPE_NOT_FOUND) {
            val notFoundView = NotFoundBinding.inflate(inflater, parent, false)
            NotFoundViewHolder(notFoundView.root)
        } else {
            val binding = ItemRowUserBinding.inflate(inflater, parent, false)
            MyViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return if (users.isEmpty()) {
            1
        } else {
            users.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (users.isEmpty()) {
            VIEW_TYPE_NOT_FOUND
        } else {
            VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == VIEW_TYPE_ITEM) {
            val user = users[position]
            (holder as MyViewHolder).bind(user)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubUserEntity>() {
            override fun areItemsTheSame(
                oldItem: GithubUserEntity,
                newItem: GithubUserEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: GithubUserEntity,
                newItem: GithubUserEntity
            ): Boolean {
                return oldItem == newItem
            }
        }

        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_NOT_FOUND = 1
    }

}
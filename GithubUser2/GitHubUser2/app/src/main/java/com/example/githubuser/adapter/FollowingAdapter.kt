package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.model.User

class FollowingAdapter(private val listFollowing: ArrayList<User>) : RecyclerView.Adapter<FollowingAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvUsername: TextView = itemView.findViewById(R.id.tvUsernameFollowing)
        var ivAvatar: ImageView = itemView.findViewById(R.id.ivAvatarFollowing)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_following, parent, false)
        return FollowingAdapter.ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.tvUsername.text = listFollowing[position].login
        Glide.with(holder.itemView.context)
            .load(listFollowing[position].avatarUrl)
            .circleCrop()
            .into(holder.ivAvatar)
    }

    override fun getItemCount(): Int = listFollowing.size
}
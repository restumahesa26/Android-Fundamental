package com.example.githubuser.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.database.UserEntity
import com.example.githubuser.databinding.ItemRowUserBinding
import com.example.githubuser.helper.UserDiffCallback
import com.example.githubuser.ui.DetailActivity

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavoriteUserViewHolder>() {

    private val listFavoriteUser = ArrayList<UserEntity>()
    fun setListFavoriteUser(listFavoriteUser: List<UserEntity>) {
        val diffCallback = UserDiffCallback(this.listFavoriteUser, listFavoriteUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavoriteUser.clear()
        this.listFavoriteUser.addAll(listFavoriteUser)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class FavoriteUserViewHolder(private val binding: ItemRowUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userEntity: UserEntity) {
            with(binding) {
                tvUsername.text = userEntity.username
                tvUrl.text = userEntity.name
                Glide.with(itemView)
                    .load(userEntity.avatar_url)
                    .circleCrop()
                    .into(ivAvatar)
                cardView.setOnClickListener {
                    val intentToDetail = Intent(it.context, DetailActivity::class.java)
                    intentToDetail.putExtra("DATA", userEntity.username)
                    intentToDetail.putExtra(DetailActivity.KEY_ID, userEntity.id)
                    it.context.startActivity(intentToDetail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteUserViewHolder {
        val binding = ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteUserViewHolder, position: Int) {
        holder.bind(listFavoriteUser[position])
    }

    override fun getItemCount(): Int {
        return listFavoriteUser.size
    }
}
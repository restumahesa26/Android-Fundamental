package com.example.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionPagerAdapter
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.model.User
import com.example.githubuser.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private var data: String? = null
    private lateinit var detailViewModel: DetailViewModel

    companion object {
        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showPagerAdapter()

        val data = intent.getStringExtra("DATA")

        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        detailViewModel.showDetailUser(data.toString()).observe(this, {
            setDetailUser(it)
        })

        detailViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        setTitle("")
    }

    private fun showPagerAdapter() {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(Intent.EXTRA_TEXT, data)
                intent.type =  "text/plain"
                startActivity(Intent.createChooser(intent, "Share the user - $data"))
                return true
            }
            else -> {
                return true
            }
        }
    }

    private fun setDetailUser(user: User) {
        setTitle(user.nama)
        Glide.with(binding.root)
            .load(user.avatarUrl)
            .circleCrop()
            .into(binding.ivAvatar)
        binding.tvName.text = user.nama
        binding.tvUsername.text = "@${user.login}"
        binding.tvRepository.text = "Repository\n${user.repository}"
        binding.tvCompany.text = "${user.company}"
        binding.tvLocation.text = "${user.location}"
        binding.tvFollowers.text = "Followers\n${user.follower}"
        binding.tvFollowing.text = "Following\n${user.following}"

        data = user.html_url
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar3.visibility = View.VISIBLE
        } else {
            binding.progressBar3.visibility = View.GONE
        }
    }
}
package com.example.githubuser.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.adapter.SectionPagerAdapter
import com.example.githubuser.database.*
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.helper.SettingPreferences
import com.example.githubuser.model.User
import com.example.githubuser.viewmodel.DetailViewModel
import com.example.githubuser.viewmodel.SettingViewModel
import com.google.android.material.tabs.TabLayoutMediator

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailActivity : AppCompatActivity() {

    private var _activityDetailBinding: ActivityDetailBinding? = null
    private val binding get() = _activityDetailBinding

    private var data: String? = null
    private var data2: String? = null
    private lateinit var detailViewModel: DetailViewModel
    var favorite: Boolean = true
    private var userEntity = UserEntity()

    private lateinit var userFavoriteViewModel: UserFavoriteViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel

    companion object {
        const val KEY_ID = "key_id"
        @StringRes
        private val TAB_TITLE = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, SettingViewModelFactory(pref)).get(
            SettingViewModel::class.java
        )
        settingViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })

        userFavoriteViewModel = obtainViewModel(this@DetailActivity)
        favoriteViewModel = obtainViewModel2(this@DetailActivity)

        userEntity.id = intent.getIntExtra(KEY_ID, 0)

        favoriteViewModel.getFavoriteById(userEntity.id).observe(this, {
            favorite = it.isNotEmpty()

            if (it.isEmpty()) {
                binding?.favBtn?.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24))
            }else {
                binding?.favBtn?.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24))
            }
        })

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

    private fun obtainViewModel(activity: AppCompatActivity): UserFavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(UserFavoriteViewModel::class.java)
    }

    private fun obtainViewModel2(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun showPagerAdapter() {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        binding?.viewPager?.adapter = sectionPagerAdapter
        TabLayoutMediator(binding!!.tabs, binding!!.viewPager) { tab, position ->
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
        Glide.with(binding!!.root)
            .load(user.avatarUrl)
            .circleCrop()
            .into(binding!!.ivAvatar)
        binding!!.tvName.text = user.nama
        binding!!.tvUsername.text = "@${user.login}"
        binding!!.tvRepository.text = "Repository\n${user.repository}"
        binding!!.tvCompany.text = "${user.company}"
        binding!!.tvLocation.text = "${user.location}"
        binding!!.tvFollowers.text = "Followers\n${user.follower}"
        binding!!.tvFollowing.text = "Following\n${user.following}"

        data = user.html_url

        binding?.favBtn?.apply {
            setOnClickListener{
                if (favorite) {
                    binding?.favBtn?.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24))
                    Toast.makeText(context, "Menghapus ${user.nama} dari Favorite", Toast.LENGTH_SHORT).show()

                    userFavoriteViewModel.delete(userEntity as UserEntity)
                } else {
                    binding?.favBtn?.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24))

                    userEntity.let { userEntity ->
                        userEntity.username = user.login
                        userEntity.name = user.nama
                        userEntity.avatar_url = user.avatarUrl
                        userEntity.repository = user.repository
                        userEntity.company = user.company
                        userEntity.location = user.location
                        userEntity.follower = user.follower
                        userEntity.following = user.following
                        userEntity.html_url = user.html_url
                    }

                    userFavoriteViewModel.insert(userEntity as UserEntity)
                    Toast.makeText(context, "Menambah ${user.nama} ke Favorite", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar3?.visibility = View.VISIBLE
        } else {
            binding?.progressBar3?.visibility = View.GONE
        }
    }
}
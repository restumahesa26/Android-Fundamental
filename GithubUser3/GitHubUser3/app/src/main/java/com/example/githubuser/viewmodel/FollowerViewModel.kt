package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.UserResponseItem
import com.example.githubuser.model.User
import com.example.githubuser.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {

    companion object {
        private const val TAG = "DetailActivity"
    }

    private val _listFollowers = MutableLiveData<ArrayList<User>>()

    val listFollower = ArrayList<User>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun showFollower(username: String): LiveData<ArrayList<User>>  {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object : Callback<ArrayList<UserResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<UserResponseItem>>,
                response: Response<ArrayList<UserResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isLoading.value = false
                        for (i in responseBody) {
                            val follower = User(i.id, i.login, i.avatarUrl)
                            listFollower.add(follower)
                        }

                        _listFollowers.value = listFollower
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<UserResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
        return _listFollowers
    }
}
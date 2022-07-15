package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.UserResponseDetail
import com.example.githubuser.model.User
import com.example.githubuser.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    companion object {
        private const val TAG = "DetailActivity"
    }

    private val _detailUser = MutableLiveData<User>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun showDetailUser(username: String?): LiveData<User> {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username.toString())
        client.enqueue(object : Callback<UserResponseDetail> {
            override fun onResponse(
                call: Call<UserResponseDetail>,
                response: Response<UserResponseDetail>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isLoading.value = false
                        val user = User(
                            responseBody.id,
                            responseBody.username,
                            responseBody.avatarUrl,
                            responseBody.name,
                            responseBody.repository,
                            responseBody.company,
                            responseBody.location,
                            responseBody.followers,
                            responseBody.following,
                            responseBody.html_url
                        )

                        _detailUser.value = user
                    }
                }
            }

            override fun onFailure(call: Call<UserResponseDetail>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
        return _detailUser
    }
}
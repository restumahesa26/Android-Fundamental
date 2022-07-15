package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.SearchUserResponse
import com.example.githubuser.UserResponseItem
import com.example.githubuser.event.Event
import com.example.githubuser.model.User
import com.example.githubuser.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val _listUsers = MutableLiveData<ArrayList<User>>()
    val listUsers: LiveData<ArrayList<User>> = _listUsers

    val listUser = ArrayList<User>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _totalCount = MutableLiveData<Event<Int>>()
    val totalCount: LiveData<Event<Int>> = _totalCount

    init {
        showAlluser()
    }

    fun showAlluser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllUser()
        client.enqueue(object : Callback<ArrayList<UserResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<UserResponseItem>>,
                response: Response<ArrayList<UserResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("MainActivity", responseBody.toString())
                    if (responseBody != null) {
                        _isLoading.value = false
                        listUser.clear()
                        for (i in responseBody) {
                            val user = User(i.login, i.avatarUrl, "", "", "", "", "", "", i.htmlUrl)
                            listUser.add(user)
                        }

                        _listUsers.value = listUser
                        _totalCount.value = Event(listUser.size)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<UserResponseItem>>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }
        })
    }

    fun searchUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchUser(username)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isLoading.value = false
                        val listUser = ArrayList<User>()
                        listUser.clear()
                        for (i in responseBody.items) {
                            val user = User(i.login, i.avatarUrl, "", "", "", "", "", "", i.htmlUrl)
                            listUser.add(user)
                        }
                        _listUsers.value = listUser
                        _totalCount.value = Event(listUser.size)
                    }
                }
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                Log.e(TAG, "onFailure : ${t.message}")
            }

        })
    }
}
package com.example.githubuser.database

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.repository.UserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun getAllUser(): LiveData<List<UserEntity>> = mUserRepository.getAllUser()

    fun getFavoriteById(id: Int): LiveData<List<UserEntity>> = mUserRepository.getFavoriteById(id)
}
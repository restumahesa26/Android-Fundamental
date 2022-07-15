package com.example.githubuser.database

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.githubuser.repository.UserRepository

class UserFavoriteViewModel(application: Application) : ViewModel() {
    private val mUserRepository: UserRepository = UserRepository(application)

    fun insert(userEntity: UserEntity) {
        mUserRepository.insert(userEntity)
    }

    fun delete(userEntity: UserEntity) {
        mUserRepository.delete(userEntity)
    }
}
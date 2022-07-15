package com.example.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuser.database.UserDao
import com.example.githubuser.database.UserEntity
import com.example.githubuser.database.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }

    fun getAllUser(): LiveData<List<UserEntity>> = mUserDao.getAllUsers()

    fun getFavoriteById(id: Int): LiveData<List<UserEntity>> = mUserDao.getUserById(id)

    fun insert(userEntity: UserEntity) {
        executorService.execute{ mUserDao.insert(userEntity) }
    }

    fun delete(userEntity: UserEntity) {
        executorService.execute{ mUserDao.delete(userEntity) }
    }
}
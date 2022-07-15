package com.example.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(userEntity: UserEntity)

    @Delete
    fun delete(userEntity: UserEntity)

    @Query("SELECT * from userentity ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Query("SELECT * from userentity WHERE id = :id")
    fun getUserById(id: Int): LiveData<List<UserEntity>>
}
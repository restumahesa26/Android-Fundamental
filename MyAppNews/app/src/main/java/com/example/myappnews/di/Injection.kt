package com.example.myappnews.di

import android.content.Context
import com.dicoding.myappnews.data.local.room.NewsDatabase
import com.dicoding.myappnews.data.remote.retrofit.ApiConfig
import com.dicoding.mynewsapp.utils.AppExecutors
import com.example.myappnews.data.NewsRepository

object Injection {
    fun provideRepository(context: Context): NewsRepository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.newsDao()
        val appExecutors = AppExecutors()
        return NewsRepository.getInstance(apiService, dao, appExecutors)
    }
}
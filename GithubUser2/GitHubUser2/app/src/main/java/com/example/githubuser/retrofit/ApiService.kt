package com.example.githubuser.retrofit

import com.example.githubuser.SearchUserResponse
import com.example.githubuser.UserResponseDetail
import com.example.githubuser.UserResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    @Headers("Authorization: token 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")
    fun getAllUser(): Call<ArrayList<UserResponseItem>>

    @GET("/search/users")
    @Headers("Authorization: token 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")
    fun searchUser(
        @Query("q") username: String
    ) : Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<UserResponseDetail>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ) : Call<ArrayList<UserResponseItem>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ) : Call<ArrayList<UserResponseItem>>
}
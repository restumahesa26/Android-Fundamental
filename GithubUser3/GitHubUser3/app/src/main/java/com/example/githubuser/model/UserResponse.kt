package com.example.githubuser

import com.google.gson.annotations.SerializedName

data class UserResponseItem(

	var id: Int = 0,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("login")
	val login: String,

	@field:SerializedName("html_url")
	val htmlUrl: String
)

data class UserResponseDetail(

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("login")
	val username: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("avatar_url")
	val avatarUrl: String,

	@field:SerializedName("company")
	val company: String,

	@field:SerializedName("location")
	val location: String,

	@field:SerializedName("public_repos")
	val repository: String,

	@field:SerializedName("followers")
	val followers: String,

	@field:SerializedName("following")
	val following: String,

	@field:SerializedName("html_url")
	val html_url: String,
)

data class SearchUserResponse(
	@field:SerializedName("total_count")
	val total_count: Int,

	@field:SerializedName("items")
	val items: List<UserResponseItem>,
)

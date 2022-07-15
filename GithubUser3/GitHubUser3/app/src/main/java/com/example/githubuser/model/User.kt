package com.example.githubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: Int = 0,
    var login: String? = null,
    var avatarUrl: String? = null,
    var nama: String? = null,
    var repository: String? = null,
    var company: String? = null,
    var location: String? = null,
    var follower: String? = null,
    var following: String? = null,
    var html_url: String? = null,
) : Parcelable

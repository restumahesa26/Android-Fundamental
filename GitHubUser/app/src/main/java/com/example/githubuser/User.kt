package com.example.githubuser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var name: String,
    var username: String,
    var avatar: Int,
    var follower: String,
    var following: String,
    var repository: String,
    var company: String,
    var location: String,
) : Parcelable

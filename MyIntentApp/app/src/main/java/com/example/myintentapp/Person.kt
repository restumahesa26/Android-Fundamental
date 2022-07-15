package com.example.myintentapp

import kotlinx.parcelize.Parcelize

import android.os.Parcel
import android.os.Parcelable

@Parcelize
data class Person (
    val name: String?,
    val age: Int?,
    val email: String?,
    val city: String?
) : Parcelable
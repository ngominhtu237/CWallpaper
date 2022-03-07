package com.tunm.cwallpaper2.data.dto

import com.google.firebase.database.IgnoreExtraProperties
import com.tunm.cwallpaper2.UserType

@IgnoreExtraProperties
data class User(
    val uid: String?, // can uid null??
    val username: String = "",
    val email: String,
    val password: String,
    val profilePhoto: String = "",
    val userType: UserType,
    val timestamp: Long = 0L
)
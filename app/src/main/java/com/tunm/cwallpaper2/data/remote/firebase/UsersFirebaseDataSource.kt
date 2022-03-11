package com.tunm.cwallpaper2.data.remote.firebase

import androidx.lifecycle.MutableLiveData
import com.tunm.cwallpaper2.data.dto.auth.AuthRequest

interface UsersFirebaseDataSource {
    suspend fun signupUser(authRequest: AuthRequest, isAdmin: Boolean): FirebaseStatus<String>
    suspend fun login(authRequest: AuthRequest): FirebaseStatus<String>
    fun isLogin(): Boolean
    fun isLoginWithAdmin(): MutableLiveData<Boolean>
    fun logout()
}
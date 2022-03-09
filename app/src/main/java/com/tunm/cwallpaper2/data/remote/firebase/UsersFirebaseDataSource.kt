package com.tunm.cwallpaper2.data.remote.firebase

import androidx.lifecycle.MutableLiveData
import com.tunm.cwallpaper2.data.dto.auth.AuthRequest

interface UsersFirebaseDataSource {
    fun signupUser(authRequest: AuthRequest, isAdmin: Boolean): MutableLiveData<FirebaseStatus<String>>
    fun login(authRequest: AuthRequest): MutableLiveData<FirebaseStatus<String>>
    fun isLogin(): Boolean
    fun isLoginWithAdmin(): MutableLiveData<Boolean>
}
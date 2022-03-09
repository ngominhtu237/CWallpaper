package com.tunm.cwallpaper2.data.repo.user

import androidx.lifecycle.MutableLiveData
import com.tunm.cwallpaper2.data.dto.auth.AuthRequest
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus

interface UsersRepository {
    fun signupUser(authRequest: AuthRequest, isAdmin: Boolean = false): MutableLiveData<FirebaseStatus<String>>
    fun isLogin(): Boolean
    fun isLoginWithAdmin(): MutableLiveData<Boolean>
    fun login(authRequest: AuthRequest): MutableLiveData<FirebaseStatus<String>>
}
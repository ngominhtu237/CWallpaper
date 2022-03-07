package com.tunm.cwallpaper2.data

import androidx.lifecycle.MutableLiveData
import com.tunm.cwallpaper2.data.dto.auth.AuthRequest
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus

interface UsersRepository {
    fun signupUser(authRequest: AuthRequest): MutableLiveData<FirebaseStatus>
    fun isLogin(): Boolean
    fun login(authRequest: AuthRequest): MutableLiveData<FirebaseStatus>
}
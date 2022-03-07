package com.tunm.cwallpaper2.data.remote.firebase

import androidx.lifecycle.MutableLiveData
import com.tunm.cwallpaper2.data.dto.auth.AuthRequest

interface UsersFirebaseDataSource {
    fun signupUser(authRequest: AuthRequest): MutableLiveData<FirebaseStatus>
    fun login(authRequest: AuthRequest): MutableLiveData<FirebaseStatus>
    fun isLogin(): Boolean
}
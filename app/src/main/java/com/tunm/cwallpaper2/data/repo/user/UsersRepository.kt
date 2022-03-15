package com.tunm.cwallpaper2.data.repo.user

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.tunm.cwallpaper2.data.dto.auth.AuthRequest
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus

interface UsersRepository {
    suspend fun signupUser(authRequest: AuthRequest, isAdmin: Boolean = false): FirebaseStatus<String>
    fun isLogin(): Boolean
    fun isLoginWithAdmin(): MutableLiveData<Boolean>
    suspend fun login(authRequest: AuthRequest): FirebaseStatus<String>
    fun logout()
    fun getCurrentUser(): FirebaseUser?
}
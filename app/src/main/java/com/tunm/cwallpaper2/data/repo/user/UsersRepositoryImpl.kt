package com.tunm.cwallpaper2.data.repo.user

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.tunm.cwallpaper2.data.dto.auth.AuthRequest
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.data.remote.firebase.UsersFirebaseDataSource
import com.tunm.cwallpaper2.utils.Validator

class UsersRepositoryImpl(
    private val usersFirebaseDataSource: UsersFirebaseDataSource
): UsersRepository {
    override suspend fun signupUser(authRequest: AuthRequest, isAdmin: Boolean): FirebaseStatus<String> {
        val firebaseStatus: FirebaseStatus<String>
        val email = authRequest.email
        val password = authRequest.password
        if (Validator.isValidEmail(email)) {
            if (Validator.isValidPassword(password)) {
                firebaseStatus = usersFirebaseDataSource.signupUser(authRequest, isAdmin)
            } else {
                firebaseStatus = FirebaseStatus.Error("Invalid password")
            }
        } else {
            firebaseStatus = FirebaseStatus.Error("Invalid email")
        }
        return firebaseStatus
    }

    override fun isLogin(): Boolean {
        return usersFirebaseDataSource.isLogin()
    }

    override fun isLoginWithAdmin(): MutableLiveData<Boolean> {
        return usersFirebaseDataSource.isLoginWithAdmin()
    }

    override suspend fun login(authRequest: AuthRequest): FirebaseStatus<String> {
        val firebaseStatus: FirebaseStatus<String>
        val email = authRequest.email
        val password = authRequest.password
        if (Validator.isValidEmail(email)) {
            if (Validator.isValidPassword(password)) {
                firebaseStatus = usersFirebaseDataSource.login(authRequest)
            } else {
                firebaseStatus = FirebaseStatus.Error("Invalid password")
            }
        } else {
            firebaseStatus = FirebaseStatus.Error("Invalid email")
        }
        return firebaseStatus
    }

    override fun logout() {
        usersFirebaseDataSource.logout()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return usersFirebaseDataSource.getCurrentUser()
    }
}
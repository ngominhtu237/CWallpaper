package com.tunm.cwallpaper2.data.repo.user

import androidx.lifecycle.MutableLiveData
import com.tunm.cwallpaper2.data.dto.auth.AuthRequest
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.data.remote.firebase.UsersFirebaseDataSource
import com.tunm.cwallpaper2.utils.Validator

class UsersRepositoryImpl(
    private val usersFirebaseDataSource: UsersFirebaseDataSource
): UsersRepository {
    override fun signupUser(authRequest: AuthRequest, isAdmin: Boolean): MutableLiveData<FirebaseStatus<String>> {
        var firebaseStatusLiveData = MutableLiveData<FirebaseStatus<String>>()
        val email = authRequest.email
        val password = authRequest.password
        if (Validator.isValidEmail(email)) {
            if (Validator.isValidPassword(password)) {
                firebaseStatusLiveData = usersFirebaseDataSource.signupUser(authRequest, isAdmin)
            } else {
                firebaseStatusLiveData.value = FirebaseStatus.Error("Invalid email")
            }
        } else {
            firebaseStatusLiveData.value = FirebaseStatus.Error("Invalid password")
        }
        return firebaseStatusLiveData
    }

    override fun isLogin(): Boolean {
        return usersFirebaseDataSource.isLogin()
    }

    override fun isLoginWithAdmin(): MutableLiveData<Boolean> {
        return usersFirebaseDataSource.isLoginWithAdmin()
    }

    override fun login(authRequest: AuthRequest): MutableLiveData<FirebaseStatus<String>> {
        var firebaseStatusLiveData = MutableLiveData<FirebaseStatus<String>>()
        val email = authRequest.email
        val password = authRequest.password
        if (Validator.isValidEmail(email)) {
            if (Validator.isValidPassword(password)) {
                firebaseStatusLiveData = usersFirebaseDataSource.login(authRequest)
            } else {
                firebaseStatusLiveData.value = FirebaseStatus.Error("Invalid email")
            }
        } else {
            firebaseStatusLiveData.value = FirebaseStatus.Error("Invalid password")
        }
        return firebaseStatusLiveData
    }
}
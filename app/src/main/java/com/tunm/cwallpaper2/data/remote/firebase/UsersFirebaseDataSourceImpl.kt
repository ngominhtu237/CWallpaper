package com.tunm.cwallpaper2.data.remote.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.tunm.cwallpaper2.UserType
import com.tunm.cwallpaper2.data.dto.User
import com.tunm.cwallpaper2.data.dto.auth.AuthRequest
import timber.log.Timber

class UsersFirebaseDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val databaseRef: DatabaseReference
): UsersFirebaseDataSource {
    override fun signupUser(authRequest: AuthRequest): MutableLiveData<FirebaseStatus> {
        val firebaseStatusLiveData = MutableLiveData<FirebaseStatus>()
        firebaseAuth.createUserWithEmailAndPassword(
            authRequest.email,
            authRequest.password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // save user to Realtime DB
                val currentUserId = firebaseAuth.currentUser?.uid
                val user = User (
                    email = authRequest.email,
                    password = authRequest.password,
                    uid = currentUserId,
                    userType = UserType.ADMIN,
                    timestamp = System.currentTimeMillis()
                )
                val usersRef = databaseRef.child("Users")
                if (currentUserId != null) {
                    usersRef.child(currentUserId).setValue(user)
                        .addOnSuccessListener {
                            firebaseStatusLiveData.value = FirebaseStatus.Success
                        }
                        .addOnFailureListener {
                            val errorMsg = "insert user db fail: ${it.message}"
                            firebaseStatusLiveData.value = FirebaseStatus.Error(errorMsg)
                        }
                } else {
                    firebaseStatusLiveData.value = FirebaseStatus.Error("currentUserId = null")
                }
            } else {
                val errorMsg = "createUserWithEmail:failure ${task.exception}"
                Timber.i(errorMsg)
                firebaseStatusLiveData.value = FirebaseStatus.Error(errorMsg)
            }
        }
        return firebaseStatusLiveData
    }

    override fun login(authRequest: AuthRequest): MutableLiveData<FirebaseStatus> {
        val firebaseStatusLiveData = MutableLiveData<FirebaseStatus>()
        firebaseAuth.signInWithEmailAndPassword(
            authRequest.email,
            authRequest.password
        ).addOnCompleteListener {  task ->
            if (task.isSuccessful) {
                firebaseStatusLiveData.value = FirebaseStatus.Success
            } else {
                val errorMsg =  "signInWithEmail:failure ${task.exception}"
                firebaseStatusLiveData.value = FirebaseStatus.Error(errorMsg)
            }
        }
        return firebaseStatusLiveData
    }

    override fun isLogin(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
package com.tunm.cwallpaper2.data.remote.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.tunm.cwallpaper2.UserType
import com.tunm.cwallpaper2.data.dto.auth.AuthRequest
import com.tunm.cwallpaper2.data.dto.auth.User
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class UsersFirebaseDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val databaseRef: DatabaseReference
): UsersFirebaseDataSource {
//    override suspend fun signupUser(authRequest: AuthRequest, isAdmin: Boolean): FirebaseStatus<String> {
//        firebaseAuth.createUserWithEmailAndPassword(
//            authRequest.email,
//            authRequest.password
//        ).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // save user to Realtime DB
//                val currentUserId = firebaseAuth.currentUser?.uid
//                val user = User (
//                    email = authRequest.email,
//                    password = authRequest.password,
//                    uid = currentUserId,
//                    userType = if (isAdmin) UserType.ADMIN.type else UserType.USER.type,
//                    timestamp = System.currentTimeMillis()
//                )
//                val usersRef = databaseRef.child("Users")
//                if (currentUserId != null) {
//                    usersRef.child(currentUserId).setValue(user)
//                        .addOnSuccessListener {
//                            return FirebaseStatus.Success("")
//                        }
//                        .addOnFailureListener {
//                            val errorMsg = "insert user db fail: ${it.message}"
//                            firebaseStatus = FirebaseStatus.Error(errorMsg)
//                        }
//                } else {
//                    firebaseStatus = FirebaseStatus.Error("currentUserId = null")
//                }
//            } else {
//                val errorMsg = "createUserWithEmail:failure ${task.exception}"
//                Timber.i(errorMsg)
//                firebaseStatus = FirebaseStatus.Error(errorMsg)
//            }
//        }
//    }

    override suspend fun signupUser(authRequest: AuthRequest, isAdmin: Boolean): FirebaseStatus<String> {
        try {
            var authResult = firebaseAuth.createUserWithEmailAndPassword(
                authRequest.email,
                authRequest.password
            ).await()

            // save user to Realtime DB
            val currentUserId = firebaseAuth.currentUser?.uid
            val user = User(
                email = authRequest.email,
                password = authRequest.password,
                uid = currentUserId,
                userType = if (isAdmin) UserType.ADMIN.type else UserType.USER.type,
                timestamp = System.currentTimeMillis()
            )
            if (currentUserId != null) {
                val usersRef = databaseRef.child("Users")
                usersRef.child(currentUserId).setValue(user).await()
            }
            return FirebaseStatus.Success("")
        } catch (e: FirebaseException) {
            return FirebaseStatus.Error("${e.message}")
        }
    }

    override suspend fun login(authRequest: AuthRequest): FirebaseStatus<String> {
        return try {
            var authResult = firebaseAuth.signInWithEmailAndPassword(
                authRequest.email,
                authRequest.password
            ).await()
            FirebaseStatus.Success("")
        } catch (e: FirebaseException) {
            FirebaseStatus.Error("${e.message}")
        }
    }

    override fun isLogin(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    /**
     * Because need a query => can't use boolean here, need use returned LiveData
     */
    override fun isLoginWithAdmin(): MutableLiveData<Boolean> {
        val fbLiveData = MutableLiveData<Boolean>()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val usersRef = databaseRef.child("Users")
            usersRef.child(currentUser.uid).get()
                .addOnSuccessListener {
                    val user = it.value as User
                    fbLiveData.value = user.userType == UserType.ADMIN.type
                }
                .addOnFailureListener {
                    Timber.d("error getting data $it")
                }
        }
        return fbLiveData
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}
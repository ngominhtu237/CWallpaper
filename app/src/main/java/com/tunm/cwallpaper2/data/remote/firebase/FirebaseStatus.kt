package com.tunm.cwallpaper2.data.remote.firebase

sealed class FirebaseStatus {
    object Success : FirebaseStatus()
    class Error(msg: String) : FirebaseStatus()
}
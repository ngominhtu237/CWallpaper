package com.tunm.cwallpaper2.data.remote.firebase

sealed class FirebaseStatus<T>(
    val data: T? = null,
    val msg: String? = null
) {
    class Success<T>(data: T) : FirebaseStatus<T>(data, null)
    class Error<T>(msg: String) : FirebaseStatus<T>(null, msg)
    class Loading<T>(data: T? = null) : FirebaseStatus<T>(data)
}
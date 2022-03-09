package com.tunm.cwallpaper2.data.remote.firebase

sealed class FirebaseStatus<T> {
    class Success<T>(data: T) : FirebaseStatus<T>()
    class Error<T>(msg: String) : FirebaseStatus<T>()
}
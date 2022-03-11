package com.tunm.cwallpaper2.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.tunm.cwallpaper2.data.remote.firebase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {

    @Singleton
    @Provides
    fun provideUsersFirebaseDataSource(
        firebaseAuth: FirebaseAuth,
        databaseRef: DatabaseReference
    ): UsersFirebaseDataSource {
        return UsersFirebaseDataSourceImpl(firebaseAuth, databaseRef)
    }

    @Singleton
    @Provides
    fun provideCategoryFirebaseDataSource(
        firebaseAuth: FirebaseAuth,
        databaseRef: DatabaseReference
    ): CategoryFirebaseDataSource {
        return CategoryFirebaseDataSourceImpl(firebaseAuth, databaseRef)
    }

    @Singleton
    @Provides
    fun providePhotoFirebaseDataSource(
        firebaseAuth: FirebaseAuth,
        databaseRef: DatabaseReference
    ): PhotosFirebaseDataSource {
        return PhotosFirebaseDataSourceImpl(firebaseAuth, databaseRef)
    }
}
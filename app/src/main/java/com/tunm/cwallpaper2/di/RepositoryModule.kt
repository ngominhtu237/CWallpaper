package com.tunm.cwallpaper2.di

import com.tunm.cwallpaper2.data.remote.firebase.CategoryFirebaseDataSource
import com.tunm.cwallpaper2.data.remote.firebase.PhotosFirebaseDataSource
import com.tunm.cwallpaper2.data.remote.firebase.UsersFirebaseDataSource
import com.tunm.cwallpaper2.data.repo.category.CategoryRepository
import com.tunm.cwallpaper2.data.repo.category.CategoryRepositoryImpl
import com.tunm.cwallpaper2.data.repo.photo.PhotoRepository
import com.tunm.cwallpaper2.data.repo.photo.PhotoRepositoryImpl
import com.tunm.cwallpaper2.data.repo.user.UsersRepository
import com.tunm.cwallpaper2.data.repo.user.UsersRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideUsersRepository(
        usersFirebaseDataSource: UsersFirebaseDataSource
    ): UsersRepository {
        return UsersRepositoryImpl(usersFirebaseDataSource)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(
        categoryFirebaseDataSource: CategoryFirebaseDataSource
    ): CategoryRepository {
        return CategoryRepositoryImpl(categoryFirebaseDataSource)
    }

    @Singleton
    @Provides
    fun providePhotoRepository(
        photosFirebaseDataSource: PhotosFirebaseDataSource
    ): PhotoRepository {
        return PhotoRepositoryImpl(photosFirebaseDataSource)
    }
}
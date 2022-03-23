package com.tunm.cwallpaper2.data.repo.photo

import androidx.lifecycle.MutableLiveData
import com.tunm.cwallpaper2.data.dto.category.Category
import com.tunm.cwallpaper2.data.dto.photo.ListPhoto
import com.tunm.cwallpaper2.data.dto.photo.Photo
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.data.remote.firebase.PhotosFirebaseDataSource

class PhotoRepositoryImpl(
    private val photosFirebaseDataSource: PhotosFirebaseDataSource
): PhotoRepository {
    override fun addPhotoByAdmin(photoRequest: Photo, categoryId: Category): MutableLiveData<FirebaseStatus<String>> {
        return photosFirebaseDataSource.addPhotoByAdmin(photoRequest, categoryId)
    }

    override fun deletePhotoByAdmin(wallpaperId: String): MutableLiveData<FirebaseStatus<String>> {
        return photosFirebaseDataSource.deletePhotoByAdmin(wallpaperId)
    }

    override fun getAllPhotos(categoryId: String): MutableLiveData<FirebaseStatus<ListPhoto>> {
        return photosFirebaseDataSource.getAllPhotos(categoryId)
    }
}
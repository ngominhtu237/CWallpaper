package com.tunm.cwallpaper2.data.remote.firebase

import androidx.lifecycle.MutableLiveData
import com.tunm.cwallpaper2.data.dto.auth.AuthRequest
import com.tunm.cwallpaper2.data.dto.category.CategoryRequest
import com.tunm.cwallpaper2.data.dto.category.ListCategory
import com.tunm.cwallpaper2.data.dto.photo.ListPhoto
import com.tunm.cwallpaper2.data.dto.photo.Photo

interface PhotosFirebaseDataSource {
    fun addPhotoByAdmin(photoRequest: Photo, categoryId: CategoryRequest): MutableLiveData<FirebaseStatus<String>>
    fun deletePhotoByAdmin(wallpaperId: String): MutableLiveData<FirebaseStatus<String>>
    fun getAllPhotos(categoryId: String): MutableLiveData<FirebaseStatus<ListPhoto>>
}
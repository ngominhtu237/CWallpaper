package com.tunm.cwallpaper2.data.repo.photo

import androidx.lifecycle.MutableLiveData
import com.tunm.cwallpaper2.data.dto.category.CategoryRequest
import com.tunm.cwallpaper2.data.dto.category.ListCategory
import com.tunm.cwallpaper2.data.dto.photo.ListPhoto
import com.tunm.cwallpaper2.data.dto.photo.Photo
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus

interface PhotoRepository {
    fun addPhotoByAdmin(photoRequest: Photo, categoryId: CategoryRequest): MutableLiveData<FirebaseStatus<String>>
    fun deletePhotoByAdmin(wallpaperId: String): MutableLiveData<FirebaseStatus<String>>
    fun getAllPhotos(categoryId: String): MutableLiveData<FirebaseStatus<ListPhoto>>
}
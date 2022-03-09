package com.tunm.cwallpaper2.data.remote.firebase

import androidx.lifecycle.MutableLiveData
import com.tunm.cwallpaper2.data.dto.category.CategoryRequest
import com.tunm.cwallpaper2.data.dto.category.ListCategory

interface CategoryFirebaseDataSource {
    fun addCategoryByAdmin(categoryRequest: CategoryRequest): MutableLiveData<FirebaseStatus<String>>
    fun deleteCategoryByAdmin(categoryId: String): MutableLiveData<FirebaseStatus<String>>
    fun getAllCategories(userId: String): MutableLiveData<FirebaseStatus<ListCategory>>
}
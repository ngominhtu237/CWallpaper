package com.tunm.cwallpaper2.data.repo.category

import androidx.lifecycle.MutableLiveData
import com.tunm.cwallpaper2.data.dto.category.CategoryRequest
import com.tunm.cwallpaper2.data.dto.category.ListCategory
import com.tunm.cwallpaper2.data.remote.firebase.CategoryFirebaseDataSource
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus

class CategoryRepositoryImpl(
    private val categoryFirebaseDataSource: CategoryFirebaseDataSource
): CategoryRepository {
    override fun addCategoryByAdmin(categoryRequest: CategoryRequest): MutableLiveData<FirebaseStatus<String>> {
        return categoryFirebaseDataSource.addCategoryByAdmin(categoryRequest)
    }

    override fun deleteCategoryByAdmin(categoryId: String): MutableLiveData<FirebaseStatus<String>> {
        return categoryFirebaseDataSource.deleteCategoryByAdmin(categoryId)
    }

    override fun getAllCategories(userId: String): MutableLiveData<FirebaseStatus<ListCategory>> {
        return categoryFirebaseDataSource.getAllCategories(userId)
    }
}
package com.tunm.cwallpaper2.data.repo.category

import androidx.lifecycle.MutableLiveData
import com.tunm.cwallpaper2.data.dto.category.Category
import com.tunm.cwallpaper2.data.remote.firebase.CategoryFirebaseDataSource
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.utils.Validator
import kotlinx.coroutines.flow.Flow

class CategoryRepositoryImpl(
    private val categoryFirebaseDataSource: CategoryFirebaseDataSource
): CategoryRepository {
    override suspend fun addCategoryByAdmin(categoryRequest: Category): FirebaseStatus<String> {
        val firebaseStatus: FirebaseStatus<String>
        if (!categoryRequest.categoryName.isNullOrEmpty()) {
            firebaseStatus = categoryFirebaseDataSource.addCategoryByAdmin(categoryRequest)
        } else {
            firebaseStatus = FirebaseStatus.Error("Please enter category name")
        }
        return firebaseStatus
    }

    override fun deleteCategoryByAdmin(categoryId: String): MutableLiveData<FirebaseStatus<String>> {
        return categoryFirebaseDataSource.deleteCategoryByAdmin(categoryId)
    }

    override suspend fun getAllCategories(userId: String): Flow<FirebaseStatus<ArrayList<Category>>> {
        return categoryFirebaseDataSource.getAllCategories(userId)
    }
}
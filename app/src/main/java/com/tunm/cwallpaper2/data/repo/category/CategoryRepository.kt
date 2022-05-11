package com.tunm.cwallpaper2.data.repo.category

import androidx.lifecycle.MutableLiveData
import com.tunm.cwallpaper2.data.dto.category.Category
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun addCategoryByAdmin(categoryRequest: Category): FirebaseStatus<String>
    fun deleteCategoryByAdmin(categoryId: String): MutableLiveData<FirebaseStatus<String>>
    suspend fun getAllCategoriesWithUser(userId: String): Flow<FirebaseStatus<ArrayList<Category>>>
    suspend fun getAllCategories(): Flow<FirebaseStatus<ArrayList<Category>>>
}
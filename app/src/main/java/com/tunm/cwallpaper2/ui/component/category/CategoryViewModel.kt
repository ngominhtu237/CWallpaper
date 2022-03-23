package com.tunm.cwallpaper2.ui.component.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tunm.cwallpaper2.data.dto.auth.AuthRequest
import com.tunm.cwallpaper2.data.dto.category.Category
import com.tunm.cwallpaper2.data.dto.category.CategoryRequest
import com.tunm.cwallpaper2.data.remote.firebase.FirebaseStatus
import com.tunm.cwallpaper2.data.repo.category.CategoryRepository
import com.tunm.cwallpaper2.data.repo.user.UsersRepository
import com.tunm.cwallpaper2.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val usersRepository: UsersRepository,
    private val categoryRepository: CategoryRepository
) : BaseViewModel() {

    private var _listCategoryResponse = MutableLiveData<FirebaseStatus<ArrayList<Category>>>()
    val listCategoryResponse: LiveData<FirebaseStatus<ArrayList<Category>>> get() = _listCategoryResponse

    private var _addCategoryResponse = MutableLiveData<FirebaseStatus<String>>()
    val addCategoryResponse: LiveData<FirebaseStatus<String>> get() = _addCategoryResponse

    fun addCategoryByAdmin(categoryRequest: Category) {
        viewModelScope.launch {
            _addCategoryResponse.value = categoryRepository.addCategoryByAdmin(categoryRequest)
        }
    }

    fun getAllCategories() {
        if (usersRepository.isLogin()) {
            val currentUserId = usersRepository.getCurrentUser()?.uid
            if (currentUserId != null) {
                viewModelScope.launch {
                    categoryRepository.getAllCategories(currentUserId)
                        .collect {
                            _listCategoryResponse.value = it
                        }
                }
            } else {
                _listCategoryResponse.value = FirebaseStatus.Error("Error: Current Id null")
            }
        } else {
            _listCategoryResponse.value = FirebaseStatus.Error("Error: User not login")
        }
    }
}
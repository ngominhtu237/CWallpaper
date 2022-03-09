package com.tunm.cwallpaper2.data.remote.firebase

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.tunm.cwallpaper2.data.dto.category.CategoryRequest
import com.tunm.cwallpaper2.data.dto.category.ListCategory
import com.tunm.cwallpaper2.data.dto.photo.ListPhoto
import com.tunm.cwallpaper2.data.dto.photo.Photo

class CategoryFirebaseDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val databaseRef: DatabaseReference
) : CategoryFirebaseDataSource {

    // Assume user already login with admin
    override fun addCategoryByAdmin(categoryRequest: CategoryRequest): MutableLiveData<FirebaseStatus<String>> {
        val firebaseStatusLiveData = MutableLiveData<FirebaseStatus<String>>()
        val currentUserId = firebaseAuth.currentUser?.uid
        if (currentUserId != null) {
            val timeStamp = System.currentTimeMillis()
            val category = CategoryRequest(
                id = "$timeStamp",
                categoryName = categoryRequest.categoryName,
                timestamp = "$timeStamp",
                uid = currentUserId
            )
            val categoryRef = databaseRef.child("Categories")
            categoryRef.child("$timeStamp").setValue(category)
                .addOnSuccessListener {
                    firebaseStatusLiveData.value = FirebaseStatus.Success("")
                }
                .addOnFailureListener {
                    val errorMsg = "Insert category fail: ${it.message}"
                    firebaseStatusLiveData.value = FirebaseStatus.Error(errorMsg)
                }
        } else {
            firebaseStatusLiveData.value = FirebaseStatus.Error("Current admin ID null")
        }
        return firebaseStatusLiveData
    }

    override fun deleteCategoryByAdmin(categoryId: String): MutableLiveData<FirebaseStatus<String>> {
        var firebaseStatusLiveData = MutableLiveData<FirebaseStatus<String>>()
        val categoryRef = databaseRef.child("Categories")
        categoryRef.child(categoryId).removeValue()
            .addOnSuccessListener {
                // And delete all photo that link to category
                firebaseStatusLiveData = deleteAllPhotoInCategory(categoryId, firebaseStatusLiveData)
            }
            .addOnFailureListener {
                val errorMsg = "Delete category fail: ${it.message}"
                firebaseStatusLiveData.value = FirebaseStatus.Error(errorMsg)
            }
        return firebaseStatusLiveData
    }

    private fun deleteAllPhotoInCategory(
        categoryId: String,
        firebaseStatusLiveData: MutableLiveData<FirebaseStatus<String>>
    ): MutableLiveData<FirebaseStatus<String>> {
        val photoRef = databaseRef.child("Photos")
        photoRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    val photo = ds.getValue(Photo::class.java)
                    if (photo != null) {
                        if (photo.categoryId == categoryId) {
                            photo.id?.let { photoRef.child(it).removeValue() }
                        }
                        firebaseStatusLiveData.value = FirebaseStatus.Success("")
                    } else {
                        firebaseStatusLiveData.value = FirebaseStatus.Error("category null")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                firebaseStatusLiveData.value =
                    FirebaseStatus.Error("loadPost:onCancelled ${error.toException()}")
            }
        })
        return firebaseStatusLiveData
    }

    override fun getAllCategories(userId: String): MutableLiveData<FirebaseStatus<ListCategory>> {
        val firebaseStatusLiveData = MutableLiveData<FirebaseStatus<ListCategory>>()
        val listCategory = arrayListOf<CategoryRequest>()
        val categoryRef = databaseRef.child("Categories")
        categoryRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listCategory.clear()
                for (ds in snapshot.children) {
                    val category = ds.getValue(CategoryRequest::class.java)
                    if (category != null) {
                        if (category.uid == userId) {
                            listCategory.add(category)
                        }
                        firebaseStatusLiveData.value =
                            FirebaseStatus.Success(ListCategory(listCategory))
                    } else {
                        firebaseStatusLiveData.value = FirebaseStatus.Error("category null")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                firebaseStatusLiveData.value =
                    FirebaseStatus.Error("loadPost:onCancelled ${error.toException()}")
            }
        })
        return firebaseStatusLiveData
    }
}
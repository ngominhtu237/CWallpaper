package com.tunm.cwallpaper2.data.remote.firebase

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.tunm.cwallpaper2.data.dto.category.Category
import com.tunm.cwallpaper2.data.dto.photo.Photo
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.sendBlocking
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class CategoryFirebaseDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val databaseRef: DatabaseReference,
    private val storageRef: StorageReference
) : CategoryFirebaseDataSource {

    // Assume user already login with admin
 /*   override suspend fun addCategoryByAdmin(categoryRequest: Category): FirebaseStatus<String> {
        val firebaseStatusLiveData = MutableLiveData<FirebaseStatus<String>>()
        val currentUserId = firebaseAuth.currentUser?.uid
        if (currentUserId != null) {
            val timeStamp = System.currentTimeMillis()
            val category = Category(
                id = "$timeStamp",
                categoryName = categoryRequest.categoryName,
                timestamp = "$timeStamp",
                uid = currentUserId,
                bgImage = categoryRequest.bgImage
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
    }*/

    override suspend fun addCategoryByAdmin(categoryRequest: Category): FirebaseStatus<String> {
        val currentUserId = firebaseAuth.currentUser?.uid
        if (currentUserId != null) {
            if (categoryRequest.bgImage != null) {
                // put file to cloud
                val filePath = categoryRequest.categoryName?.replace("\\s".toRegex(), "")
                val timeStamp = System.currentTimeMillis()
                val categoryRef = storageRef.child("${filePath}/${timeStamp}")
                try {
                    val uploadResult = categoryRef.putFile(Uri.parse(categoryRequest.bgImage))
                        .await()
                    val uriTask = uploadResult.storage.downloadUrl
                    while (!uriTask.isSuccessful);
                    val uploadBgImage = "${uriTask.result}"

                    val timeStamp = System.currentTimeMillis()
                    val category = Category(
                        id = "$timeStamp",
                        categoryName = categoryRequest.categoryName,
                        timestamp = "$timeStamp",
                        uid = currentUserId,
                        bgImage = uploadBgImage
                    )
                    return addCategoryToDatabase(category)
                } catch (e: FirebaseException) {
                    return FirebaseStatus.Error("${e.message}")
                }
            } else {
                val timeStamp = System.currentTimeMillis()
                val category = Category(
                    id = "$timeStamp",
                    categoryName = categoryRequest.categoryName,
                    timestamp = "$timeStamp",
                    uid = currentUserId
                )
                return addCategoryToDatabase(category)
            }

        } else {
            return FirebaseStatus.Error("Current admin ID null")
        }
    }

    private suspend fun addCategoryToDatabase(category: Category): FirebaseStatus<String> {
        return try {
            val categoryRef = databaseRef.child("Categories")
            val result = categoryRef.child("${category.timestamp}").setValue(category)
                .await()

            FirebaseStatus.Success("")
        } catch (e: FirebaseException) {
            FirebaseStatus.Error("${e.message}")
        }
    }

    override fun deleteCategoryByAdmin(categoryId: String): MutableLiveData<FirebaseStatus<String>> {
        var firebaseStatusLiveData = MutableLiveData<FirebaseStatus<String>>()
        val categoryRef = databaseRef.child("Categories")
        categoryRef.child(categoryId).removeValue()
            .addOnSuccessListener {
                // And delete all photo that link to category
                firebaseStatusLiveData =
                    deleteAllPhotoInCategory(categoryId, firebaseStatusLiveData)
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

//    override fun getAllCategories(userId: String): FirebaseStatus<ArrayList<Category>> {
//        val firebaseStatusLiveData = FirebaseStatus<ArrayList<Category>>
//        val listCategory = arrayListOf<Category>()
//        val categoryRef = databaseRef.child("Categories")
//        categoryRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                listCategory.clear()
//                for (ds in snapshot.children) {
//                    val category = ds.getValue(Category::class.java)
//                    if (category != null) {
//                        if (category.uid == userId) {
//                            listCategory.add(category)
//                        }
//                        firebaseStatusLiveData.value = FirebaseStatus.Success(listCategory)
//                    } else {
//                        firebaseStatusLiveData.value = FirebaseStatus.Error("category null")
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                firebaseStatusLiveData.value =
//                    FirebaseStatus.Error("loadPost:onCancelled ${error.toException()}")
//            }
//        })
//        return firebaseStatusLiveData
//    }

    override suspend fun getAllCategoriesWithUser(userId: String): Flow<FirebaseStatus<ArrayList<Category>>> {
        val listCategory = arrayListOf<Category>()
        val categoryRef = databaseRef.child("Categories")

        return callbackFlow {
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listCategory.clear()
                    for (ds in snapshot.children) {
                        val category = ds.getValue(Category::class.java)
                        if (category != null) {
                            if (category.uid == userId) {
                                listCategory.add(category)
                            }
                        } else {
                            // this@callbackFlow.trySendBlocking(FirebaseStatus.Error<ArrayList<Category>>("Category null"))
                        }
                        trySend(FirebaseStatus.Success(listCategory))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySendBlocking(FirebaseStatus.Error<ArrayList<Category>>(error.message))
                }

            }
            categoryRef.addValueEventListener(valueEventListener)
            awaitClose { categoryRef.removeEventListener(valueEventListener) }
        }
    }

    override suspend fun getAllCategories(): Flow<FirebaseStatus<ArrayList<Category>>> {
        val listCategory = arrayListOf<Category>()
        val categoryRef = databaseRef.child("Categories")

        return callbackFlow {
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    listCategory.clear()
                    for (ds in snapshot.children) {
                        val category = ds.getValue(Category::class.java)
                        if (category != null) {
                            listCategory.add(category)
                        }
                        trySend(FirebaseStatus.Success(listCategory))
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    this@callbackFlow.trySendBlocking(FirebaseStatus.Error<ArrayList<Category>>(error.message))
                }

            }
            categoryRef.addValueEventListener(valueEventListener)
            awaitClose { categoryRef.removeEventListener(valueEventListener) }
        }
    }
}
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

class PhotosFirebaseDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val databaseRef: DatabaseReference
): PhotosFirebaseDataSource {
    override fun addPhotoByAdmin(photoRequest: Photo, categoryId: CategoryRequest): MutableLiveData<FirebaseStatus<String>> {
        val firebaseStatusLiveData = MutableLiveData<FirebaseStatus<String>>()
        val currentUserId = firebaseAuth.currentUser?.uid
        if (currentUserId != null) {
            val timeStamp = System.currentTimeMillis()
            val photo = Photo(
                id = "$timeStamp",
                photoName = photoRequest.photoName,
                photoDescription = photoRequest.photoDescription,
                url = photoRequest.url,
                uid = currentUserId,
                categoryId = photoRequest.categoryId,
                timestamp = "$timeStamp"
            )
            val photoRef = databaseRef.child("Photos")
            photoRef.child("$timeStamp").setValue(photo)
                .addOnSuccessListener {
                    firebaseStatusLiveData.value = FirebaseStatus.Success("")
                }
                .addOnFailureListener {
                    val errorMsg = "Insert photo fail: ${it.message}"
                    firebaseStatusLiveData.value = FirebaseStatus.Error(errorMsg)
                }
        } else {
            firebaseStatusLiveData.value = FirebaseStatus.Error("Current admin ID null")
        }
        return firebaseStatusLiveData
    }

    override fun deletePhotoByAdmin(wallpaperId: String): MutableLiveData<FirebaseStatus<String>> {
        val firebaseStatusLiveData = MutableLiveData<FirebaseStatus<String>>()
        val photoRef = databaseRef.child("Photos")
        photoRef.child(wallpaperId).removeValue()
            .addOnSuccessListener {
                firebaseStatusLiveData.value = FirebaseStatus.Success("")
            }
            .addOnFailureListener {
                val errorMsg = "Delete photo fail: ${it.message}"
                firebaseStatusLiveData.value = FirebaseStatus.Error(errorMsg)
            }
        return firebaseStatusLiveData
    }

    override fun getAllPhotos(categoryId: String): MutableLiveData<FirebaseStatus<ListPhoto>> {
        val firebaseStatusLiveData = MutableLiveData<FirebaseStatus<ListPhoto>>()
        val listPhoto = arrayListOf<Photo>()
        val photoRef = databaseRef.child("Photos")
        photoRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listPhoto.clear()
                for(ds in snapshot.children) {
                    val photo = ds.getValue(Photo::class.java)
                    if (photo != null) {
                        if (photo.categoryId == categoryId) {
                            listPhoto.add(photo)
                        }
                        firebaseStatusLiveData.value = FirebaseStatus.Success(ListPhoto(listPhoto))
                    } else {
                        firebaseStatusLiveData.value = FirebaseStatus.Error("category null")
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                firebaseStatusLiveData.value = FirebaseStatus.Error("loadPost:onCancelled ${error.toException()}")
            }
        })
        return firebaseStatusLiveData
    }

}
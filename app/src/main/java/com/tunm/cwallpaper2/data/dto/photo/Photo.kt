package com.tunm.cwallpaper2.data.dto.photo

data class Photo(
    val id: String?,
    val photoName: String,
    val photoDescription: String?,
    val url: String,        // can't null
    val uid: String,        // can't null
    val categoryId: String,   // can't null
    val timestamp: String?,

    val downloadCount: Long? = 0,
    val viewCount: Long? = 0,
    val favoriteCount: Long? = 0
)

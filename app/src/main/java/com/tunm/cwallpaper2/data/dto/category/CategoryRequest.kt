package com.tunm.cwallpaper2.data.dto.category

data class CategoryRequest(
    val id: String?,
    val categoryName: String,
    val timestamp: String?,
    val uid: String // can't null because category need to be created by certain user
)

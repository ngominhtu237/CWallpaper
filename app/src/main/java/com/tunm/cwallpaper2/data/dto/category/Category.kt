package com.tunm.cwallpaper2.data.dto.category

import com.tunm.cwallpaper2.ui.base.recyclerview.ListItemAdapter
import java.io.Serializable

data class Category(
    val id: String? = null,
    val categoryName: String? = null,
    val bgImage: String? = null,
    val timestamp: String? = null,
    val uid: String? = null // can't null because category need to be created by certain user
): ListItemAdapter, Serializable {

}

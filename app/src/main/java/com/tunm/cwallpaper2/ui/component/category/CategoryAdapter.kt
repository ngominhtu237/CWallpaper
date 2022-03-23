package com.tunm.cwallpaper2.ui.component.category

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.data.dto.category.Category
import com.tunm.cwallpaper2.databinding.ItemCategoryBinding
import com.tunm.cwallpaper2.ui.base.recyclerview.BaseAdapter

class CategoryAdapter(
    private val listCategory: List<Category>
): BaseAdapter<ItemCategoryBinding, Category>(listCategory) {

    override fun bind(binding: ItemCategoryBinding, item: Category) {
        binding.apply {
            tvCategoryName.text = item.categoryName
            if (item.bgImage != null) {
                Glide.with(ivBackground.context)
                    .load(item.bgImage)
                    .into(ivBackground)
            } else {
                ivBackground.setImageResource(R.drawable.defaut_holder_bg)
            }
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Int) -> ItemCategoryBinding
        get() =  { inflater, parent, _ ->
            ItemCategoryBinding.inflate(inflater, parent, false)
        }
}
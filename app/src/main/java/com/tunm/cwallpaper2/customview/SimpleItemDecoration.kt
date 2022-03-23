package com.tunm.cwallpaper2.customview

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.tunm.cwallpaper2.utils.ConvertUtils

class SimpleItemDecoration(context: Context, space: Int = 10) : RecyclerView.ItemDecoration() {

    private val spaceInDp = ConvertUtils.dpToPx(context, space)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {

//        outRect.left = spaceInDp
//        outRect.right = spaceInDp
        outRect.bottom = spaceInDp
        // Add top margin only for the first item to avoid double space between items
//        if (parent.getChildAdapterPosition(view) == 0) {
//            outRect.top = spaceInDp
//        }
    }
}
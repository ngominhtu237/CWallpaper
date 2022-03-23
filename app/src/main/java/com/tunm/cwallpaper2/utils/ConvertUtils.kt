package com.tunm.cwallpaper2.utils

import android.content.Context

class ConvertUtils {
    companion object {
        fun dpToPx(context: Context, dp: Int): Int {
            return (dp * context.resources.displayMetrics.density).toInt()
        }
    }
}
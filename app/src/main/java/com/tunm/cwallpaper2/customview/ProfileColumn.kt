package com.tunm.cwallpaper2.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.withStyledAttributes
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.databinding.ProfileColumnBinding

class ProfileColumn @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : RelativeLayout(context, attrs, defStyle) {
    private var binding: ProfileColumnBinding =
        ProfileColumnBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.withStyledAttributes(attrs, R.styleable.ProfileColumnView) {
            val textName = getString(R.styleable.ProfileColumnView_textName)
            val icon = getDrawable(R.styleable.ProfileColumnView_customIcon)
            binding.apply {
                tvName.text = textName
                ivIcon.setImageDrawable(icon)
            }
        }
    }
}
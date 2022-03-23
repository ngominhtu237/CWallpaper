package com.tunm.cwallpaper2.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.content.withStyledAttributes
import com.tunm.cwallpaper2.R
import com.tunm.cwallpaper2.databinding.FillColumnBinding

class FillColumn @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
) : RelativeLayout(context, attrs, defStyle) {
    private var binding: FillColumnBinding =
        FillColumnBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.withStyledAttributes(attrs, R.styleable.FillColumnView) {
            val textName = getString(R.styleable.FillColumnView_fill_column_text_hint)
            binding.apply {
                edtName.hint = textName
            }
        }
    }

    fun getCategoryName(): String {
        return binding.edtName.text.toString()
    }
}
package com.example.noteapp.cleannoteapp.presentation.data_binding

import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory

class BindingAdapters {

    companion object {
         private var onClickListener: ColorCategoryBinding? = null

        @BindingAdapter("android:colorCategoryOnClick")
        @JvmStatic
        fun colorCategoryOnClick(view: LinearLayout, colorCategory: ColorCategory) {
            view.setOnClickListener {
                onClickListener?.userSelectedColor(colorCategory)
            }
        }

        fun setItemOnClickListener(listener: ColorCategoryBinding) {
            onClickListener = listener
        }
    }
}
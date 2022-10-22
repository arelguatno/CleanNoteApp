package com.example.noteapp.cleannoteapp.presentation.data_binding

import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.SortBy
import com.example.noteapp.cleannoteapp.models.enums.ViewBy

class BindingAdapters {

    companion object {
        private var onClickListener: ColorCategoryBinding? = null
        private var sortBy: SortByBinding? = null
        private var viewBy: ViewByBinding? = null

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

        @BindingAdapter("android:sortByOnClick")
        @JvmStatic
        fun sortByOnClick(view: LinearLayout, sort: SortBy) {
            view.setOnClickListener {
                sortBy?.onClick(sort)
            }
        }

        fun setSortByOnClickListener(listener: SortByBinding) {
            sortBy = listener
        }

        @BindingAdapter("android:viewByOnClick")
        @JvmStatic
        fun viewByOnClick(view: LinearLayout, sort: ViewBy) {
            view.setOnClickListener {
                viewBy?.onClick(sort)
            }
        }

        fun setViewByOnClickListener(listener: ViewByBinding) {
            viewBy = listener
        }
    }
}
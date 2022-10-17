package com.example.noteapp.cleannoteapp.presentation.data_binding

import com.example.noteapp.cleannoteapp.models.enums.ColorCategory

interface ColorCategoryBinding {
    fun userSelectedColor(colorBinding: ColorCategory)
}
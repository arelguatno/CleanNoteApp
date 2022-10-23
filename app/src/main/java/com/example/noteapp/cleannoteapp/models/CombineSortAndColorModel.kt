package com.example.noteapp.cleannoteapp.models

import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.SortBy

data class CombineSortAndColorModel(
    val colorCategory: ColorCategory,
    val sortBy: SortBy
)
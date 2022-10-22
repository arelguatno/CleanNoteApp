package com.example.noteapp.cleannoteapp.presentation.data_binding

import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.SortBy

interface SortByBinding {
    fun onClick(sortBy: SortBy)
}
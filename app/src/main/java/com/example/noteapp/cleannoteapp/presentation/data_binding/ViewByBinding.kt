package com.example.noteapp.cleannoteapp.presentation.data_binding

import com.example.noteapp.cleannoteapp.models.enums.SortBy
import com.example.noteapp.cleannoteapp.models.enums.ViewBy

interface ViewByBinding {
    fun onClick(viewBy: ViewBy)
}
package com.example.noteapp.cleannoteapp.models

import com.example.noteapp.cleannoteapp.models.enums.ColorCategory

data class NoteModel(
    var header: String? = "",
    var date: String? = "",
    var body: String? = "",
    var category: ColorCategory? = ColorCategory.OPTION_ONE
)

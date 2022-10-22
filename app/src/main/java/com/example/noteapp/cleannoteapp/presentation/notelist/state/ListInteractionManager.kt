package com.example.noteapp.cleannoteapp.presentation.notelist.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.ViewBy

class ListInteractionManager {

    private val _viewByMenu: MutableLiveData<ViewBy> =
        MutableLiveData(ViewBy.Default)

    val viewByMenu: LiveData<ViewBy>
        get() = _viewByMenu

    fun setViewByMenu(state: ViewBy) {
        _viewByMenu.value = state
    }

    private val _colorCategory: MutableLiveData<ColorCategory> =
        MutableLiveData(ColorCategory.ALL_NOTES)

    val colorCategory: LiveData<ColorCategory>
        get() = _colorCategory

    fun setColorCategory(state: ColorCategory) {
        _colorCategory.value = state
    }
}
package com.example.noteapp.cleannoteapp.presentation.notelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.ViewBy
import com.example.noteapp.cleannoteapp.presentation.common.BaseViewModel
import com.example.noteapp.cleannoteapp.presentation.notelist.state.ListInteractionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale.Category
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(

) : BaseViewModel() {

    private val noteInteractionManager: ListInteractionManager = ListInteractionManager()

    val viewByMenuInteractionState: LiveData<ViewBy>
        get() = noteInteractionManager.viewByMenu

    fun setViewByMenuState(state: ViewBy) {
        noteInteractionManager.setViewByMenu(state)
    }

    val viewByColorInteractionState: LiveData<ColorCategory>
        get() = noteInteractionManager.colorCategory

    fun setByColorCategory(state: ColorCategory) {
        noteInteractionManager.setColorCategory(state)
    }
}
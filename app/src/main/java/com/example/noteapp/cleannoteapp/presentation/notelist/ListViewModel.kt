package com.example.noteapp.cleannoteapp.presentation.notelist

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.ViewBy
import com.example.noteapp.cleannoteapp.presentation.common.BaseViewModel
import com.example.noteapp.cleannoteapp.presentation.notelist.state.ListInteractionManager
import com.example.noteapp.cleannoteapp.util.PreferenceKeys
import com.example.noteapp.cleannoteapp.util.extensions.save
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val sharedPref: SharedPreferences
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

    internal fun loadDefaultColor() {
        val color =
            GsonBuilder().create().fromJson(getColorSettingsMenu(), ColorCategory::class.java)
        setByColorCategory(color)
    }

    private fun getColorSettingsMenu(): String? {
        return sharedPref.getString(
            PreferenceKeys.LIST_VIEW_COLOR_THEME,
            getCategoryAllNotes().toString()
        )
    }

    fun saveDefaultColor(color: ColorCategory) {
        sharedPref.save(
            PreferenceKeys.LIST_VIEW_COLOR_THEME,
            GsonBuilder().create().toJson(color)
        )
    }
}
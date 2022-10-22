package com.example.noteapp.cleannoteapp.presentation.notelist

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.SortBy
import com.example.noteapp.cleannoteapp.models.enums.ViewBy
import com.example.noteapp.cleannoteapp.presentation.common.BaseViewModel
import com.example.noteapp.cleannoteapp.presentation.notelist.state.ListInteractionManager
import com.example.noteapp.cleannoteapp.util.PreferenceKeys
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.LIST_VIEW_COLOR_THEME
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.LIST_VIEW_SORT_BY
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

    val sortByInteractionState: LiveData<SortBy>
        get() = noteInteractionManager.sortBy

    fun setSortCategory(state: SortBy) {
        noteInteractionManager.setSortBy(state)
    }

    internal fun loadDefaultColor() {
        val ggg = sharedPref.getString(
            LIST_VIEW_COLOR_THEME,
            getCategoryAllNotes().toString()
        )

        val color =
            GsonBuilder().create().fromJson(ggg, ColorCategory::class.java)
        setByColorCategory(color)
    }

    fun saveDefaultColor(color: ColorCategory) {
        sharedPref.save(
            LIST_VIEW_COLOR_THEME,
            GsonBuilder().create().toJson(color)
        )
    }

    internal fun defaultSortBy() {
        val ggg = sharedPref.getString(
            LIST_VIEW_SORT_BY,
            SortBy.COLOR.toString()
        )
        val sortBy =
            GsonBuilder().create().fromJson(ggg, SortBy::class.java)
        setSortCategory(sortBy)
    }

    fun saveSortBy(value: SortBy) {
        sharedPref.save(
            LIST_VIEW_SORT_BY,
            GsonBuilder().create().toJson(value)
        )
    }
}
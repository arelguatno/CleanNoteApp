package com.example.noteapp.cleannoteapp.presentation.notelist

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import com.example.noteapp.cleannoteapp.models.CombineSortAndColorModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.SortBy
import com.example.noteapp.cleannoteapp.models.enums.ViewBy
import com.example.noteapp.cleannoteapp.presentation.common.BaseViewModel
import com.example.noteapp.cleannoteapp.presentation.notelist.state.ListInteractionManager
import com.example.noteapp.cleannoteapp.util.PreferenceKeys
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.LIST_VIEW_COLOR_THEME
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.LIST_VIEW_SORT_BY
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.LIST_VIEW_VIEW_BY
import com.example.noteapp.cleannoteapp.util.extensions.save
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val sharedPref: SharedPreferences
) : BaseViewModel() {

    private val noteInteractionManager: ListInteractionManager = ListInteractionManager()

    val viewByMenuInteractionState: LiveData<ViewBy>
        get() = noteInteractionManager.viewByMenu


    val viewByColorInteractionState: LiveData<ColorCategory>
        get() = noteInteractionManager.colorCategory

    val sortByInteractionState: LiveData<SortBy>
        get() = noteInteractionManager.sortBy

    val combineObserver =
        combine(
            viewByColorInteractionState.asFlow(),
            sortByInteractionState.asFlow()
        ) { colorCat, sortBy ->
            CombineSortAndColorModel(colorCat, sortBy)
        }

    fun setViewByMenuState(state: ViewBy) {
        noteInteractionManager.setViewByMenu(state)
    }

    fun setByColorCategory(state: ColorCategory) {
        noteInteractionManager.setColorCategory(state)
    }


    fun setSortCategory(state: SortBy) {
        noteInteractionManager.setSortBy(state)
    }

    internal fun loadDefaultColor() {
        val sharedValue = sharedPref.getString(
            LIST_VIEW_COLOR_THEME,
            getCategoryAllNotes().toString()
        )

        val color =
            GsonBuilder().create().fromJson(sharedValue, ColorCategory::class.java)
        setByColorCategory(color)
    }

    fun saveDefaultColor(color: ColorCategory) {
        sharedPref.save(
            LIST_VIEW_COLOR_THEME,
            GsonBuilder().create().toJson(color)
        )
    }

    internal fun loadDefaultSortBy() {
        val sharedValue = sharedPref.getString(
            LIST_VIEW_SORT_BY,
            getSortByModifiedTime().toString()
        )
        val sortBy =
            GsonBuilder().create().fromJson(sharedValue, SortBy::class.java)
        setSortCategory(sortBy)
    }

    fun saveSortBy(value: SortBy) {
        sharedPref.save(
            LIST_VIEW_SORT_BY,
            GsonBuilder().create().toJson(value)
        )
    }

    internal fun loadDefaultViewBy() {
        val sharedValue = sharedPref.getString(
            LIST_VIEW_VIEW_BY,
            getViewByGrid().toString()
        )
        val viewBy =
            GsonBuilder().create().fromJson(sharedValue, ViewBy::class.java)
        setViewByMenuState(viewBy)
    }

    fun saveViewBy(value: ViewBy) {
        sharedPref.save(
            LIST_VIEW_VIEW_BY,
            GsonBuilder().create().toJson(value)
        )
    }
}
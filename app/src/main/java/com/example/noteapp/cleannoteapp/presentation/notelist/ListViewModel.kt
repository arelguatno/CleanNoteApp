package com.example.noteapp.cleannoteapp.presentation.notelist

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.asFlow
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.models.CombineSortAndColorModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.MenuActions
import com.example.noteapp.cleannoteapp.models.enums.SortBy
import com.example.noteapp.cleannoteapp.models.enums.ViewBy
import com.example.noteapp.cleannoteapp.presentation.common.BaseViewModel
import com.example.noteapp.cleannoteapp.presentation.notelist.state.ListInteractionManager
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListScreenState
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListToolbarState
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteRepository
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.LIST_VIEW_COLOR_THEME
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.LIST_VIEW_SORT_BY
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.LIST_VIEW_VIEW_BY
import com.example.noteapp.cleannoteapp.util.extensions.save
import com.example.noteapp.cleannoteapp.util.printLogD
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val sharedPref: SharedPreferences,
    private val crudRepo: NoteRepository
) : BaseViewModel() {

    val noteInteractionManager: ListInteractionManager = ListInteractionManager()

    val toolbarState: LiveData<NoteListToolbarState>
        get() = noteInteractionManager.toolbarState

    val listScreenInterActionState: LiveData<NoteListScreenState>
        get() = noteInteractionManager.listScreenState

    val toolbarStateValue: NoteListToolbarState?
        get() = noteInteractionManager.toolbarState.value

    val viewByMenuInteractionState: LiveData<ViewBy>
        get() = noteInteractionManager.viewByMenu

    val viewByColorInteractionState: LiveData<ColorCategory>
        get() = noteInteractionManager.colorCategory

    val selectedNotesInteractionState: LiveData<ArrayList<NoteModel>>
        get() = noteInteractionManager.selectedNotes

    val sortByInteractionState: LiveData<SortBy>
        get() = noteInteractionManager.sortBy

    val combineObserver =
        combine(
            viewByColorInteractionState.asFlow(),
            sortByInteractionState.asFlow()
        ) { colorCat, sortBy ->
            CombineSortAndColorModel(colorCat, sortBy)
        }

    fun getSelectedNotes() = noteInteractionManager.getSelectedNotes()
    fun getSelectedNotesID() = noteInteractionManager.getSelectedNotesID()

    fun setToolbarState(state: NoteListToolbarState) = noteInteractionManager.setToolbarState(state)

    fun addOrRemoveNoteFromSelectedList(note: NoteModel) =
        noteInteractionManager.addOrRemoveNoteFromSelectedList(note)

    fun isMultiSelectionStateActive() = noteInteractionManager.isMultiSelectionStateActive()

    fun isMainListViewScreenActive() = noteInteractionManager.isMainListViewScreenActive()

    fun isBinScreenActive() = noteInteractionManager.isBinScreenActive()

    fun isArchiveScreenActive() = noteInteractionManager.isArchiveScreenActive()

    fun clearSelectedNotes() = noteInteractionManager.clearSelectedNotes()

    fun isNoteSelected(note: NoteModel): Boolean = noteInteractionManager.isNoteSelected(note)

    fun setViewByMenuState(state: ViewBy) {
        noteInteractionManager.setViewByMenu(state)
    }

    fun setListScreenState(state: NoteListScreenState) {
        noteInteractionManager.setListScreenState(state)
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

    fun deleteNotes() {
        if (getSelectedNotes().size > 0) {

            clearSelectedNotes()
        } else {
            printLogD("classname", "empty")
        }
    }

    fun getToastArchiveMessage(tmpData: ArrayList<Int>): Int {
        return if (tmpData.size == 1) {
            R.string.note_archived
        } else {
            R.string.note_archived_plural
        }
    }

    fun getToastBinMessage(tmpData: ArrayList<Int>): Int {
        return if (tmpData.size == 1) {
            R.string.note_deleted
        } else {
            R.string.note_deleted_plural
        }
    }

    fun getRestoreMessage(tmpData: ArrayList<Int>): Int {
        return if (tmpData.size == 1) {
            R.string.note_restored
        } else {
            R.string.notes_restored
        }
    }

    fun getSingularMessage(action: MenuActions): Int {
        return if (action == MenuActions.Bin) {
            R.string.note_deleted
        } else {
            R.string.note_archived
        }
    }
}
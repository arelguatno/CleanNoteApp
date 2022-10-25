package com.example.noteapp.cleannoteapp.presentation.notelist.state

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.SortBy
import com.example.noteapp.cleannoteapp.models.enums.ViewBy
import com.example.noteapp.cleannoteapp.presentation.notelist.state.NoteListToolbarState.*
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel

class ListInteractionManager {
    private val _selectedNotes: MutableLiveData<ArrayList<NoteModel>> = MutableLiveData()

    private val _toolbarState: MutableLiveData<NoteListToolbarState> =
        MutableLiveData(ListViewState)

    val selectedNotes: LiveData<ArrayList<NoteModel>>
        get() = _selectedNotes

    val toolbarState: LiveData<NoteListToolbarState>
        get() = _toolbarState

    fun setToolbarState(state: NoteListToolbarState) {
        _toolbarState.value = state
    }

    fun getSelectedNotes(): ArrayList<NoteModel> = _selectedNotes.value ?: ArrayList()

    fun isMultiSelectionStateActive(): Boolean {
        return _toolbarState.value.toString() == MultiSelectionState.toString()
    }

    fun isNoteSelected(note: NoteModel): Boolean {
        return _selectedNotes.value?.contains(note) ?: false
    }

    fun clearSelectedNotes() {
        _selectedNotes.value = null
    }

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

    private val _sortBy: MutableLiveData<SortBy> =
        MutableLiveData(SortBy.MODIFIED_TIME)

    val sortBy: LiveData<SortBy>
        get() = _sortBy

    fun setSortBy(state: SortBy) {
        _sortBy.value = state
    }

    fun addOrRemoveNoteFromSelectedList(note: NoteModel) {
        var list = _selectedNotes.value
        if (list == null) {
            list = ArrayList()
        }
        if (list.contains(note)) {
            list.remove(note)
        } else {
            list.add(note)
        }
        _selectedNotes.value = list
    }
}
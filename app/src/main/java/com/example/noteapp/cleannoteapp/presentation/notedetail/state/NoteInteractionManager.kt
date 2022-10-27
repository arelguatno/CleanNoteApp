package com.example.noteapp.cleannoteapp.presentation.notedetail.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteapp.cleannoteapp.models.ViewStateModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.DefaultState
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.EditState
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.ViewState.*
import java.util.*


class NoteInteractionManager {
    private val _noteTitleState: MutableLiveData<NoteInteractionState> =
        MutableLiveData(DefaultState)

    private val _noteBodyState: MutableLiveData<NoteInteractionState> =
        MutableLiveData(DefaultState)

    private val _colorCategoryState: MutableLiveData<NoteInteractionState> =
        MutableLiveData(DefaultState)

    private val _themeState: MutableLiveData<NoteInteractionState> =
        MutableLiveData(DefaultState)

    private val _pinnedState: MutableLiveData<NoteInteractionState> =
        MutableLiveData(DefaultState)

    private val _viewState: MutableLiveData<ViewStateModel> =
        MutableLiveData(ViewStateModel(NewItem, null))

    private val _themeSelected: MutableLiveData<ColorCategory> =
        MutableLiveData(ColorCategory.OPTION_ONE)

    private val _currentDate: MutableLiveData<Date> =
        MutableLiveData(Date())

    private val _pinnedIsClicked: MutableLiveData<Boolean> =
        MutableLiveData(false)

    val pinnedIsClicked: LiveData<Boolean>
        get() = _pinnedIsClicked

    val viewState: LiveData<ViewStateModel>
        get() = _viewState

    private val pinnedState: LiveData<NoteInteractionState>
        get() = _pinnedState

    val currentDate: LiveData<Date>
        get() = _currentDate

    val noteTitleState: LiveData<NoteInteractionState>
        get() = _noteTitleState

    val noteBodyState: LiveData<NoteInteractionState>
        get() = _noteBodyState

    private val colorCategoryState: LiveData<NoteInteractionState>
        get() = _colorCategoryState

    val themeSelected: LiveData<ColorCategory>
        get() = _themeSelected

    val themeState: LiveData<NoteInteractionState>
        get() = _themeState

    fun setPinnedIsClicked(state: Boolean) {
        _pinnedIsClicked.value = state
    }

    fun setThemeSelected(state: ColorCategory) {
        _themeSelected.value = state
    }

    fun setThemeState(state: NoteInteractionState) {
        _themeState.value = state
    }

    fun setCurrentDate(state: Date) {
        _currentDate.value = state
    }

    fun exitEditState() {
        _noteTitleState.value = DefaultState
        _noteBodyState.value = DefaultState
        _pinnedState.value = DefaultState
        _colorCategoryState.value = DefaultState
    }

    fun setViewState(state: ViewStateModel) {
        if (_viewState.toString() != state.toString()) {
            _viewState.value = state
        }
    }

    fun setPinnedState(state: NoteInteractionState) {
        if (pinnedState.toString() != state.toString()) {
            _pinnedState.value = state
        }
    }

    fun isEditingTitle() = noteTitleState.value.toString() == EditState.toString()

    fun isEditingColor() = colorCategoryState.value.toString() == EditState.toString()

    fun isEditingBody() = noteBodyState.value.toString() == EditState.toString()

    fun isEditingPin() = pinnedState.value.toString() == EditState.toString()


    fun setNewNoteBodyState(state: NoteInteractionState) {
        if (noteBodyState.toString() != state.toString()) {
            _noteBodyState.value = state
            when (state) {
                is EditState -> {
                    _noteTitleState.value = DefaultState
                }
                else -> {}
            }
        }
    }

    fun setColorCategoryState(state: NoteInteractionState) {
        if (colorCategoryState.toString() != state.toString()) {
            _colorCategoryState.value = state
        }
    }

    fun setNewNoteTitleState(state: NoteInteractionState) {
        if (noteTitleState.toString() != state.toString()) {
            _noteTitleState.value = state
            when (state) {
                is EditState -> {
                    _noteBodyState.value = DefaultState
                }
                else -> {}
            }
        }
    }

    // return true if title, body, color, pin are in EditState
    fun checkEditState() = noteTitleState.value.toString() == EditState.toString()
            || noteBodyState.value.toString() == EditState.toString()
            || colorCategoryState.value.toString() == EditState.toString()
            || pinnedState.value.toString() == EditState.toString()
}
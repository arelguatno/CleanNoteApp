package com.example.noteapp.cleannoteapp.presentation.notedetail.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.DefaultState
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.EditState
import java.util.*


class NoteInteractionManager {
    private val _noteTitleState: MutableLiveData<NoteInteractionState> =
        MutableLiveData(DefaultState)

    private val _noteBodyState: MutableLiveData<NoteInteractionState> =
        MutableLiveData(DefaultState)

    private val _themeSelected: MutableLiveData<ColorCategory> =
        MutableLiveData(ColorCategory.OPTION_ONE)

    private val _themeState: MutableLiveData<NoteInteractionState> =
        MutableLiveData(DefaultState)

    private val _currentDate: MutableLiveData<Date> =
        MutableLiveData(Date())

    val currentDate: LiveData<Date>
        get() = _currentDate

    val noteTitleState: LiveData<NoteInteractionState>
        get() = _noteTitleState

    val noteBodyState: LiveData<NoteInteractionState>
        get() = _noteBodyState

    val themeSelected: LiveData<ColorCategory>
        get() = _themeSelected

    val themeState: LiveData<NoteInteractionState>
        get() = _themeState

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
    }

    fun isEditingTitle() = noteTitleState.value.toString() == EditState.toString()

    fun isEditingBody() = noteBodyState.value.toString() == EditState.toString()

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

    // return true if either title or body are in EditState
    fun checkEditState() = noteTitleState.value.toString() == EditState.toString()
            || noteBodyState.value.toString() == EditState.toString()
}
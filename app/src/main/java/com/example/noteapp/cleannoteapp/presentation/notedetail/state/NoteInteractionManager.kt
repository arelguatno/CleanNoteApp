package com.example.noteapp.cleannoteapp.presentation.notedetail.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.DefaultState
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.EditState


class NoteInteractionManager {
    private val _noteTitleState: MutableLiveData<NoteInteractionState> =
        MutableLiveData(DefaultState)

    private val _noteBodyState: MutableLiveData<NoteInteractionState> =
        MutableLiveData(DefaultState)

    private val _colorState: MutableLiveData<ColorCategory> =
        MutableLiveData(ColorCategory.OPTION_ONE)

    private val _themeState: MutableLiveData<NoteInteractionState> =
        MutableLiveData(DefaultState)

    val noteTitleState: LiveData<NoteInteractionState>
        get() = _noteTitleState

    val noteBodyState: LiveData<NoteInteractionState>
        get() = _noteBodyState

    val colorSelected: LiveData<ColorCategory>
        get() = _colorState

    val themeState: LiveData<NoteInteractionState>
        get() = _themeState


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

    fun setColorCategory(state: ColorCategory) {
        _colorState.value = state
    }

    fun setThemeState(state: NoteInteractionState) {
        _themeState.value = state
    }


    fun isEditingTitle() = noteTitleState.value.toString() == EditState.toString()
    fun isEditingBody() = noteBodyState.value.toString() == EditState.toString()

    fun exitEditState() {
        _noteTitleState.value = DefaultState
        _noteBodyState.value = DefaultState
    }

    // return true if either title or body are in EditState
    fun checkEditState() = noteTitleState.value.toString() == EditState.toString()
            || noteBodyState.value.toString() == EditState.toString()
}
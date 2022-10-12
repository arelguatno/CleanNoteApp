package com.example.noteapp.cleannoteapp.presentation.notedetail.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.*


class NoteInteractionManager {
    private val _noteTitleState: MutableLiveData<NoteInteractionState> =
        MutableLiveData(DefaultState)

    private val _noteBodyState: MutableLiveData<NoteInteractionState> =
        MutableLiveData(DefaultState)

    val noteTitleState: LiveData<NoteInteractionState>
        get() = _noteTitleState

    val noteBodyState: LiveData<NoteInteractionState>
        get() = _noteBodyState


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

    fun isEditingBody() = noteBodyState.value.toString() == EditState.toString()
}
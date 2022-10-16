package com.example.noteapp.cleannoteapp.presentation.notedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionManager
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddUpdateViewModel @Inject constructor(
) : ViewModel() {
    private val noteInteractionManager: NoteInteractionManager = NoteInteractionManager()

    val noteTitleInteractionState: LiveData<NoteInteractionState>
        get() = noteInteractionManager.noteTitleState

    val noteBodyInteractionState: LiveData<NoteInteractionState>
        get() = noteInteractionManager.noteBodyState

    val pinnedInteractionState: LiveData<NoteInteractionState>
        get() = noteInteractionManager.pinnedState

    val themeSelected: LiveData<ColorCategory>
        get() = noteInteractionManager.themeSelected

    val setThemeState: LiveData<NoteInteractionState>
        get() = noteInteractionManager.themeState

    val currentInteractionDate: LiveData<Date>
        get() = noteInteractionManager.currentDate

    fun isEditingBody() = noteInteractionManager.isEditingBody()

    fun isEditingTitle() = noteInteractionManager.isEditingTitle()

    fun isEditingPin() = noteInteractionManager.isEditingPin()

    fun checkEditState() = noteInteractionManager.checkEditState()

    fun exitEditState() = noteInteractionManager.exitEditState()

    fun setNoteInteractionBodyState(state: NoteInteractionState) {
        noteInteractionManager.setNewNoteBodyState(state)
    }

    fun setNoteInteractionTitleState(state: NoteInteractionState) {
        noteInteractionManager.setNewNoteTitleState(state)
    }

    fun setThemeSelected(color: ColorCategory) {
        noteInteractionManager.setThemeSelected(color)
    }

    fun setThemeState(state: NoteInteractionState) {
        noteInteractionManager.setThemeState(state)
    }

    fun setPinnedState(state: NoteInteractionState) {
        noteInteractionManager.setPinnedState(state)
    }

    fun setCurrentDate(date: Date) {
        noteInteractionManager.setCurrentDate(date)
    }
}
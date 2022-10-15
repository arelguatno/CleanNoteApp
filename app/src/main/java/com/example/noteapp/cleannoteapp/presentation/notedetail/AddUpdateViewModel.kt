package com.example.noteapp.cleannoteapp.presentation.notedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionManager
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddUpdateViewModel @Inject constructor(
) : ViewModel() {
    private val noteInteractionManager: NoteInteractionManager = NoteInteractionManager()

    val noteTitleInteractionState: LiveData<NoteInteractionState>
        get() = noteInteractionManager.noteTitleState

    val noteBodyInteractionState: LiveData<NoteInteractionState>
        get() = noteInteractionManager.noteBodyState

    val colorSelected: LiveData<ColorCategory>
        get() = noteInteractionManager.colorSelected

    fun isEditingBody() = noteInteractionManager.isEditingBody()
    fun isEditingTitle() = noteInteractionManager.isEditingTitle()
    fun checkEditState() = noteInteractionManager.checkEditState()
    fun exitEditState() = noteInteractionManager.exitEditState()

    fun setNoteInteractionBodyState(state: NoteInteractionState) {
        noteInteractionManager.setNewNoteBodyState(state)
    }

    fun setNoteInteractionTitleState(state: NoteInteractionState) {
        noteInteractionManager.setNewNoteTitleState(state)
    }

    fun setColorCategory(color: ColorCategory) {
        noteInteractionManager.setColorCategory(color)
    }
}
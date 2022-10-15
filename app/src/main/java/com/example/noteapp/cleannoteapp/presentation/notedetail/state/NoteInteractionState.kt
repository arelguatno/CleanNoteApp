package com.example.noteapp.cleannoteapp.presentation.notedetail.state

import com.example.noteapp.cleannoteapp.models.enums.ColorCategory

sealed class NoteInteractionState {

    object EditState : NoteInteractionState() {
        override fun toString(): String {
            return "EditState"
        }
    }

    object DefaultState : NoteInteractionState() {
        override fun toString(): String {
            return "DefaultState"
        }
    }
}

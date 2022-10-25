package com.example.noteapp.cleannoteapp.presentation.notelist.state

sealed class NoteListToolbarState {

    object MultiSelectionState : NoteListToolbarState() {

        override fun toString(): String {
            return "MultiSelectionState"
        }
    }

    object ListViewState : NoteListToolbarState() {

        override fun toString(): String {
            return "ListViewState"
        }
    }
}
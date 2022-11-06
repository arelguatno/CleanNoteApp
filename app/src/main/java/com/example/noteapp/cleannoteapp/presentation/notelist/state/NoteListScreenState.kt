package com.example.noteapp.cleannoteapp.presentation.notelist.state

sealed class NoteListScreenState {

    object MainListView : NoteListScreenState() {

        override fun toString(): String {
            return "MainListView"
        }
    }

    object ArchiveView : NoteListScreenState() {

        override fun toString(): String {
            return "ArchiveView"
        }
    }

    object BinView : NoteListScreenState() {

        override fun toString(): String {
            return "BinView"
        }
    }
}
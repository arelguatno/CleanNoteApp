package com.example.noteapp.cleannoteapp.presentation.notedetail.state

import kotlinx.serialization.Serializable


sealed class ViewState: java.io.Serializable {

    object NewItem : ViewState() {
        override fun toString(): String {
            return "NewItem"
        }
    }

    object EditItem : ViewState() {
        override fun toString(): String {
            return "EditItem"
        }
    }
}

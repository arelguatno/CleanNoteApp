package com.example.noteapp.cleannoteapp.models

import com.example.noteapp.cleannoteapp.presentation.notedetail.state.ViewState
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel
import java.io.Serializable

data class ViewStateModel(
    val state: ViewState,
    val noteModel: NoteModel?
) : Serializable
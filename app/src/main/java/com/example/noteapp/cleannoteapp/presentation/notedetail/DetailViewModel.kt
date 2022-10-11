package com.example.noteapp.cleannoteapp.presentation.notedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.cleannoteapp.models.NoteModel
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    fun insertRecord(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRecord(note)
        }
    }
}
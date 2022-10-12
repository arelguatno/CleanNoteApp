package com.example.noteapp.cleannoteapp.room_database.note_table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.noteapp.cleannoteapp.util.extensions.appMainFormat
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    fun insertRecord(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRecord(note)
        }
    }

    fun fetchRecordData(): Flow<PagingData<NoteModel>> {
        return Pager(PagingConfig(pageSize = 10)) {
            repository.fetchWalletsRecord()
        }.flow.cachedIn(viewModelScope).map { notesModel ->
            notesModel.filter {
                convertDateToString(it)
            }
        }
    }

    private fun convertDateToString(noteModel: NoteModel): Boolean {
        noteModel.dates?.dateModifiedStringValue =
            noteModel.dates?.dateModified?.appMainFormat().toString()
        return true
    }
}
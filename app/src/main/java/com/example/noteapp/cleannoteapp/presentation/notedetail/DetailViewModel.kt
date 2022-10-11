package com.example.noteapp.cleannoteapp.presentation.notedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.noteapp.cleannoteapp.models.NoteModel
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    fun fetchRecordData(): Flow<PagingData<NoteModel>> {
        return Pager(PagingConfig(pageSize = 10)) {
            repository.fetchWalletsRecord()
        }.flow.cachedIn(viewModelScope)
    }
}
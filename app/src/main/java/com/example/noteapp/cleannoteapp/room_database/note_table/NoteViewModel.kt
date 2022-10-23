package com.example.noteapp.cleannoteapp.room_database.note_table

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.SortBy
import com.example.noteapp.cleannoteapp.presentation.common.BaseViewModel
import com.example.noteapp.cleannoteapp.util.extensions.appMainFormat
import com.example.noteapp.cleannoteapp.util.printLogD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : BaseViewModel() {

    fun insertRecord(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRecord(note)
        }
    }

    private fun convertDateToString(noteModel: NoteModel): Boolean {
        noteModel.dates?.dateModifiedStringValue =
            noteModel.dates?.dateModified?.appMainFormat().toString()
        return true
    }

    private fun checkIfHeaderIsEmpty(noteModel: NoteModel): Boolean {
        if (noteModel.header.isNullOrEmpty()) {
            noteModel.header = noteModel.body.toString()
        }
        return true
    }

    fun fetchListViewRecords(
        colorCategory: ColorCategory,
        sortBy: SortBy
    ): Flow<PagingData<NoteModel>> {
        when (sortBy) {
            SortBy.MODIFIED_TIME -> {
                if (colorCategory == getCategoryAllNotes()) {
                    return Pager(PagingConfig(pageSize = 10)) {
                        repository.fetchAllSortByModifiedTime()
                    }.flow.cachedIn(viewModelScope).map { notesModel ->
                        notesModel.filter {
                            convertDateToString(it)
                            checkIfHeaderIsEmpty(it)
                        }
                    }
                } else {
                    return Pager(PagingConfig(pageSize = 10)) {
                        repository.fetchPerColorSortByModifiedTime(colorCategory)
                    }.flow.cachedIn(viewModelScope).map { notesModel ->
                        notesModel.filter {
                            convertDateToString(it)
                            checkIfHeaderIsEmpty(it)
                        }
                    }
                }
            }
            SortBy.CREATED_TIME -> {
                if (colorCategory == getCategoryAllNotes()) {
                    return Pager(PagingConfig(pageSize = 10)) {
                        repository.fetchAllSortByCreatedTime()
                    }.flow.cachedIn(viewModelScope).map { notesModel ->
                        notesModel.filter {
                            convertDateToString(it)
                            checkIfHeaderIsEmpty(it)
                        }
                    }
                } else {
                    return Pager(PagingConfig(pageSize = 10)) {
                        repository.fetchPerColorSortByCreatedTime(colorCategory)
                    }.flow.cachedIn(viewModelScope).map { notesModel ->
                        notesModel.filter {
                            convertDateToString(it)
                            checkIfHeaderIsEmpty(it)
                        }
                    }
                }
            }
            SortBy.REMINDER_TIME -> {  // TODO
                if (colorCategory == getCategoryAllNotes()) {
                    return Pager(PagingConfig(pageSize = 10)) {
                        repository.fetchWalletsRecord()
                    }.flow.cachedIn(viewModelScope).map { notesModel ->
                        notesModel.filter {
                            convertDateToString(it)
                            checkIfHeaderIsEmpty(it)
                        }
                    }
                } else {
                    return Pager(PagingConfig(pageSize = 10)) {
                        repository.fetchNotesPerCategory(colorCategory)
                    }.flow.cachedIn(viewModelScope).map { notesModel ->
                        notesModel.filter {
                            convertDateToString(it)
                            checkIfHeaderIsEmpty(it)
                        }
                    }
                }
            }
            SortBy.COLOR -> {
                if (colorCategory == getCategoryAllNotes()) {
                    return Pager(PagingConfig(pageSize = 10)) {
                        repository.fetchAllSortByColor()
                    }.flow.cachedIn(viewModelScope).map { notesModel ->
                        notesModel.filter {
                            convertDateToString(it)
                            checkIfHeaderIsEmpty(it)
                        }
                    }
                } else {
                    return Pager(PagingConfig(pageSize = 10)) {
                        repository.fetchPerColorSortByColor(colorCategory)
                    }.flow.cachedIn(viewModelScope).map { notesModel ->
                        notesModel.filter {
                            convertDateToString(it)
                            checkIfHeaderIsEmpty(it)
                        }
                    }
                }
            }
        }
    }
}
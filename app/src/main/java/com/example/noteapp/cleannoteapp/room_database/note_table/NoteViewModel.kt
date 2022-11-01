package com.example.noteapp.cleannoteapp.room_database.note_table

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.SortBy
import com.example.noteapp.cleannoteapp.presentation.common.BaseViewModel
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
) : BaseViewModel() {

    val fetchBinAndArchiveCounting = repository.fetchBinAndArchiveCounts().asLiveData()

    fun insertRecord(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertRecord(note)
        }
    }

    fun updateRecord(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecord(note)
        }
    }

    fun deleteRecord(note: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteRecord(note)
        }
    }

    fun updateMultipleColorItems(list: ArrayList<Int>, colorCategory: ColorCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMultipleColorItems(list, colorCategory)
        }
    }

    fun transferItemsToBin(list: ArrayList<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.transferItemsToBin(list)
        }
    }

    fun transferItemsToArchive(list: ArrayList<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.transferItemsToArchive(list)
        }
    }

    fun undoTransferItemsToBin(list: ArrayList<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.undoTransferItemsToBin(list)
        }
    }

    fun undoTransferItemsToArchive(list: ArrayList<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.undoTransferItemsToArchive(list)
        }
    }

    fun deleteListOfData(list: ArrayList<NoteModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteListOfData(list)
        }
    }

    fun insertListOfData(list: ArrayList<NoteModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertListOfData(list)
        }
    }

    private fun convertDateToString(noteModel: NoteModel): Boolean {
        noteModel.dates?.dateModifiedStringValue =
            noteModel.dates?.dateModified?.appMainFormat().toString()
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
                        }
                    }
                } else {
                    return Pager(PagingConfig(pageSize = 10)) {
                        repository.fetchPerColorSortByModifiedTime(colorCategory)
                    }.flow.cachedIn(viewModelScope).map { notesModel ->
                        notesModel.filter {
                            convertDateToString(it)
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
                        }
                    }
                } else {
                    return Pager(PagingConfig(pageSize = 10)) {
                        repository.fetchPerColorSortByCreatedTime(colorCategory)
                    }.flow.cachedIn(viewModelScope).map { notesModel ->
                        notesModel.filter {
                            convertDateToString(it)
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
                        }
                    }
                } else {
                    return Pager(PagingConfig(pageSize = 10)) {
                        repository.fetchNotesPerCategory(colorCategory)
                    }.flow.cachedIn(viewModelScope).map { notesModel ->
                        notesModel.filter {
                            convertDateToString(it)
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
                        }
                    }
                } else {
                    return Pager(PagingConfig(pageSize = 10)) {
                        repository.fetchPerColorSortByColor(colorCategory)
                    }.flow.cachedIn(viewModelScope).map { notesModel ->
                        notesModel.filter {
                            convertDateToString(it)
                        }
                    }
                }
            }
        }
    }
}
package com.example.noteapp.cleannoteapp.room_database.note_table

import androidx.paging.PagingSource
import androidx.room.Query
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    suspend fun insertRecord(note: NoteModel) {
        return noteDao.insertRecord(note)
    }

    suspend fun updateRecord(note: NoteModel){
        return noteDao.updateRecord(note)
    }

    fun fetchWalletsRecord(): PagingSource<Int, NoteModel> {
        return noteDao.fetchNotesData()
    }

    fun fetchNotesPerCategory(category: ColorCategory): PagingSource<Int, NoteModel> {
        return noteDao.fetchNotesPerCategory(category)
    }


    // A
    fun fetchAllSortByModifiedTime(): PagingSource<Int, NoteModel> {
        return noteDao.fetchAllSortByModifiedTime()
    }

    fun fetchAllSortByCreatedTime(): PagingSource<Int, NoteModel> {
        return noteDao.fetchAllSortByCreatedTime()
    }

    fun fetchAllSortByColor(): PagingSource<Int, NoteModel> {
        return noteDao.fetchAllSortByColor()
    }

    // B
    fun fetchPerColorSortByModifiedTime(category: ColorCategory): PagingSource<Int, NoteModel> {
        return noteDao.fetchPerColorSortByModifiedTime(category)
    }

    fun fetchPerColorSortByCreatedTime(category: ColorCategory): PagingSource<Int, NoteModel> {
        return noteDao.fetchPerColorSortByCreatedTime(category)
    }

    fun fetchPerColorSortByColor(category: ColorCategory): PagingSource<Int, NoteModel> {
        return noteDao.fetchPerColorSortByColor(category)
    }
}
package com.example.noteapp.cleannoteapp.room_database.note_table

import androidx.paging.PagingSource
import androidx.room.Query
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    suspend fun insertRecord(note: NoteModel) {
        return noteDao.insertRecord(note)
    }

    suspend fun updateRecord(note: NoteModel) {
        return noteDao.updateRecord(note)
    }

    suspend fun deleteRecord(note: NoteModel) {
        return noteDao.deleteRecord(note)
    }

    suspend fun deleteListOfData(list: ArrayList<NoteModel>) {
        return noteDao.deleteListOfData(list)
    }

    suspend fun insertListOfData(list: ArrayList<NoteModel>) {
        return noteDao.insertListOfData(list)
    }

    fun updateMultipleColorItems(list: ArrayList<Int>, colorCategory: ColorCategory) {
        return noteDao.updateMultipleColorItems(list, colorCategory)
    }

    fun transferItemsToBin(list: ArrayList<Int>) {
        return noteDao.transferItemsToBin(list)
    }

    fun transferItemsToArchive(list: ArrayList<Int>) {
        return noteDao.transferItemsToArchive(list)
    }

    fun undoTransferItemsToBin(list: ArrayList<Int>) {
        return noteDao.undoTransferItemsToBin(list)
    }

    fun undoTransferItemsToArchive(list: ArrayList<Int>) {
        return noteDao.undoTransferItemsToArchive(list)
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

    fun fetchBinAndArchiveCounts(): Flow<List<ReportingModel>> {
        return noteDao.fetchBinAndArchiveCounts()
    }
}
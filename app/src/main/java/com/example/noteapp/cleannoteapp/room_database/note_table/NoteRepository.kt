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

    fun undoDeletedArchiveItems(list: ArrayList<Int>) {
        return noteDao.undoDeletedArchiveItems(list)
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


    // Archive - A
    fun fetchAllArchiveSortByModifiedTime(): PagingSource<Int, NoteModel> {
        return noteDao.fetchAllArchiveSortByModifiedTime()
    }

    fun fetchAllArchiveSortByCreatedTime(): PagingSource<Int, NoteModel> {
        return noteDao.fetchAllArchiveSortByCreatedTime()
    }

    fun fetchAllArchiveSortByColor(): PagingSource<Int, NoteModel> {
        return noteDao.fetchAllArchiveSortByColor()
    }

    // Archive - B
    fun fetchArchivePerColorSortByModifiedTime(category: ColorCategory): PagingSource<Int, NoteModel> {
        return noteDao.fetchArchivePerColorSortByModifiedTime(category)
    }

    fun fetchArchivePerColorSortByCreatedTime(category: ColorCategory): PagingSource<Int, NoteModel> {
        return noteDao.fetchArchivePerColorSortByCreatedTime(category)
    }

    fun fetchArchivePerColorSortByColor(category: ColorCategory): PagingSource<Int, NoteModel> {
        return noteDao.fetchArchivePerColorSortByColor(category)
    }

    // Bin - A
    fun fetchAllBinSortByModifiedTime(): PagingSource<Int, NoteModel> {
        return noteDao.fetchAllBinSortByModifiedTime()
    }

    fun fetchAllBinSortByCreatedTime(): PagingSource<Int, NoteModel> {
        return noteDao.fetchAllBinSortByCreatedTime()
    }

    fun fetchAllBinSortByColor(): PagingSource<Int, NoteModel> {
        return noteDao.fetchAllBinSortByColor()
    }

    // Bin - B
    fun fetchBinPerColorSortByModifiedTime(category: ColorCategory): PagingSource<Int, NoteModel> {
        return noteDao.fetchBinPerColorSortByModifiedTime(category)
    }

    fun fetchBinPerColorSortByCreatedTime(category: ColorCategory): PagingSource<Int, NoteModel> {
        return noteDao.fetchBinPerColorSortByCreatedTime(category)
    }

    fun fetchBinPerColorSortByColor(category: ColorCategory): PagingSource<Int, NoteModel> {
        return noteDao.fetchBinPerColorSortByColor(category)
    }
}
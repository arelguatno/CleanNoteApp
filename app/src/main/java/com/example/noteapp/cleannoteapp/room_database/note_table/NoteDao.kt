package com.example.noteapp.cleannoteapp.room_database.note_table

import androidx.paging.PagingSource
import androidx.room.*
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(noteModel: NoteModel)

    @Update
    suspend fun updateRecord(noteModel: NoteModel)

    @Delete
    suspend fun deleteListOfData(idList: ArrayList<NoteModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfData(list: ArrayList<NoteModel>)

    @Delete
    suspend fun deleteRecord(note: NoteModel)

    @Query("UPDATE notes_table SET category = :colorCategory WHERE id IN (:list)")
    fun updateMultipleColorItems(list: ArrayList<Int>, colorCategory: ColorCategory)

    @Query("UPDATE notes_table SET bin = 1, archive = 0 WHERE id IN (:list)")
    fun transferItemsToBin(list: ArrayList<Int>)

    @Query("UPDATE notes_table SET archive = 1, bin = 0 WHERE id IN (:list)")
    fun transferItemsToArchive(list: ArrayList<Int>)

    @Query("UPDATE notes_table SET archive = 0, bin = 0 WHERE id IN (:list)")
    fun undoDeletedArchiveItems(list: ArrayList<Int>)

    // All Notes
    @Query("SELECT * FROM notes_table WHERE bin = 0 AND archive = 0 ORDER BY pinned DESC, dates_dateModified DESC")
    fun fetchNotesData(): PagingSource<Int, NoteModel>

    // Per Category
    @Query("SELECT * FROM notes_table WHERE bin = 0 AND archive = 0 AND category = :category ORDER BY pinned DESC, dates_dateModified DESC")
    fun fetchNotesPerCategory(category: ColorCategory): PagingSource<Int, NoteModel>

    // A
    @Query("SELECT * FROM notes_table WHERE bin = 0 AND archive = 0 ORDER BY pinned DESC, dates_dateModified DESC")
    fun fetchAllSortByModifiedTime(): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE bin = 0 AND archive = 0 ORDER BY pinned DESC, dates_dateCreated DESC")
    fun fetchAllSortByCreatedTime(): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE bin = 0 AND archive = 0 ORDER BY pinned DESC, category DESC, dates_dateModified DESC")
    fun fetchAllSortByColor(): PagingSource<Int, NoteModel>

    // B
    @Query("SELECT * FROM notes_table WHERE bin = 0 AND archive = 0 AND category = :category ORDER BY pinned DESC, dates_dateModified DESC")
    fun fetchPerColorSortByModifiedTime(category: ColorCategory): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE bin = 0 AND archive = 0 AND category = :category ORDER BY pinned DESC, dates_dateCreated DESC")
    fun fetchPerColorSortByCreatedTime(category: ColorCategory): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE bin = 0 AND archive = 0 AND category = :category ORDER BY pinned DESC, category DESC, dates_dateModified DESC")
    fun fetchPerColorSortByColor(category: ColorCategory): PagingSource<Int, NoteModel>

    @Query("SELECT SUM(archive) reporting_archive, SUM(bin) reporting_bin FROM notes_table")
    fun fetchBinAndArchiveCounts(): Flow<List<ReportingModel>>

    // Archive - A
    @Query("SELECT * FROM notes_table WHERE archive = 1 ORDER BY pinned DESC, dates_dateModified DESC")
    fun fetchAllArchiveSortByModifiedTime(): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE archive = 1 ORDER BY pinned DESC, dates_dateCreated DESC")
    fun fetchAllArchiveSortByCreatedTime(): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE archive = 1 ORDER BY pinned DESC, category DESC, dates_dateModified DESC")
    fun fetchAllArchiveSortByColor(): PagingSource<Int, NoteModel>

    // Archive - B
    @Query("SELECT * FROM notes_table WHERE archive = 1 AND category = :category ORDER BY pinned DESC, dates_dateModified DESC")
    fun fetchArchivePerColorSortByModifiedTime(category: ColorCategory): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE archive = 1 AND category = :category ORDER BY pinned DESC, dates_dateCreated DESC")
    fun fetchArchivePerColorSortByCreatedTime(category: ColorCategory): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE archive = 1 AND category = :category ORDER BY pinned DESC, category DESC, dates_dateModified DESC")
    fun fetchArchivePerColorSortByColor(category: ColorCategory): PagingSource<Int, NoteModel>


    // Bin - A
    @Query("SELECT * FROM notes_table WHERE bin = 1 ORDER BY pinned DESC, dates_dateModified DESC")
    fun fetchAllBinSortByModifiedTime(): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE bin = 1 ORDER BY pinned DESC, dates_dateCreated DESC")
    fun fetchAllBinSortByCreatedTime(): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE bin = 1 ORDER BY pinned DESC, category DESC, dates_dateModified DESC")
    fun fetchAllBinSortByColor(): PagingSource<Int, NoteModel>

    // Bin - B
    @Query("SELECT * FROM notes_table WHERE bin = 1 AND category = :category ORDER BY pinned DESC, dates_dateModified DESC")
    fun fetchBinPerColorSortByModifiedTime(category: ColorCategory): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE bin = 1 AND category = :category ORDER BY pinned DESC, dates_dateCreated DESC")
    fun fetchBinPerColorSortByCreatedTime(category: ColorCategory): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE bin = 1 AND category = :category ORDER BY pinned DESC, category DESC, dates_dateModified DESC")
    fun fetchBinPerColorSortByColor(category: ColorCategory): PagingSource<Int, NoteModel>
}
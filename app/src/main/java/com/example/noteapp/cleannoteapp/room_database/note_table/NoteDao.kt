package com.example.noteapp.cleannoteapp.room_database.note_table

import androidx.paging.PagingSource
import androidx.room.*
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import java.util.Locale.Category

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

    // All Notes
    @Query("SELECT * FROM notes_table ORDER BY pinned DESC, dates_dateModified DESC")
    fun fetchNotesData(): PagingSource<Int, NoteModel>

    // Per Category
    @Query("SELECT * FROM notes_table WHERE category = :category ORDER BY pinned DESC, dates_dateModified DESC")
    fun fetchNotesPerCategory(category: ColorCategory): PagingSource<Int, NoteModel>

    // A
    @Query("SELECT * FROM notes_table ORDER BY pinned DESC, dates_dateModified DESC")
    fun fetchAllSortByModifiedTime(): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table ORDER BY pinned DESC, dates_dateCreated DESC")
    fun fetchAllSortByCreatedTime(): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table ORDER BY pinned DESC, category DESC, dates_dateModified DESC")
    fun fetchAllSortByColor(): PagingSource<Int, NoteModel>

    // B
    @Query("SELECT * FROM notes_table WHERE category = :category ORDER BY pinned DESC, dates_dateModified DESC")
    fun fetchPerColorSortByModifiedTime(category: ColorCategory): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE category = :category ORDER BY pinned DESC, dates_dateCreated DESC")
    fun fetchPerColorSortByCreatedTime(category: ColorCategory): PagingSource<Int, NoteModel>

    @Query("SELECT * FROM notes_table WHERE category = :category ORDER BY pinned DESC, category DESC, dates_dateModified DESC")
    fun fetchPerColorSortByColor(category: ColorCategory): PagingSource<Int, NoteModel>
}
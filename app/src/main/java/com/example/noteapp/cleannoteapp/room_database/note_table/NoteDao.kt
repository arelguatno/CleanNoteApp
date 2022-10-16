package com.example.noteapp.cleannoteapp.room_database.note_table

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(noteModel: NoteModel)

    @Query("SELECT * FROM notes_table ORDER BY pinned DESC, pinned ASC")
    fun fetchNotesData(): PagingSource<Int, NoteModel>
}
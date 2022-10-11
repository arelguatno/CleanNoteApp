package com.example.noteapp.cleannoteapp.room_database.note_table

import androidx.paging.PagingSource
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    suspend fun insertRecord(note: NoteModel) {
        return noteDao.insertRecord(note)
    }

    fun fetchWalletsRecord(): PagingSource<Int, NoteModel> {
        return noteDao.fetchNotesData()
    }
}
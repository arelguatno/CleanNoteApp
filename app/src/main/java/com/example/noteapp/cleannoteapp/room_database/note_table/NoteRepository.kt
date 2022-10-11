package com.example.noteapp.cleannoteapp.room_database.note_table

import com.example.noteapp.cleannoteapp.models.NoteModel
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    suspend fun insertRecord(note: NoteModel) {
        return noteDao.insertRecord(note)
    }
}
package com.example.noteapp.cleannoteapp.room_database.note_table

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.noteapp.cleannoteapp.models.NoteModel

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(noteModel: NoteModel)
}
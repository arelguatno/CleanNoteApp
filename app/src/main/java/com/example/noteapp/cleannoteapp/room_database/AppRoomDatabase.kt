package com.example.noteapp.cleannoteapp.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.noteapp.cleannoteapp.models.NoteModel
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteDao


@Database(
    entities = [NoteModel::class],
    version = 1,
    exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
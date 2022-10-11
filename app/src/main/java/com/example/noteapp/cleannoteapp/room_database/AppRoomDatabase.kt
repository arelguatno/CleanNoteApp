package com.example.noteapp.cleannoteapp.room_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteModel
import com.example.noteapp.cleannoteapp.room_database.note_table.NoteDao
import com.example.noteapp.cleannoteapp.room_database.type_converter.DateConverter


@Database(
    entities = [NoteModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}
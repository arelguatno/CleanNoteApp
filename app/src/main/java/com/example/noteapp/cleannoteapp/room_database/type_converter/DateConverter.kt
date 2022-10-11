package com.example.noteapp.cleannoteapp.room_database.type_converter

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        val ff = Calendar.getInstance()
        if (date != null) {
            ff.time = date
        }
        println(ff.time.toString())
        return date?.time
    }
}
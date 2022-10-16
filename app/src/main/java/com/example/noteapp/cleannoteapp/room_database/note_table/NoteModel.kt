package com.example.noteapp.cleannoteapp.room_database.note_table

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import java.io.Serializable
import java.util.Date

@Entity(tableName = "notes_table")
data class NoteModel(
    var header: String? = "",
    var body: String? = "",
    @Embedded(prefix = "dates_")
    var dates: Dates?,
    var category: ColorCategory? = ColorCategory.OPTION_ONE,
    var pinned: Boolean = false
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

data class Dates(
    val dateCreated: Date = Date(),
    var dateModified: Date = Date(),
    var dateModifiedStringValue: String = ""
) : Serializable

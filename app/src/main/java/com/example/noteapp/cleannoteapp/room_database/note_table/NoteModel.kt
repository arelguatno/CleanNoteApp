package com.example.noteapp.cleannoteapp.models

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
    var dates: Dates? = Dates(dateCreated = Date(), dateModified = Date()),
    var category: ColorCategory? = ColorCategory.OPTION_ONE
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
data class Dates(
    val dateCreated: Date = Date(),
    val dateModified: Date = Date()
) : Serializable

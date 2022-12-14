package com.example.noteapp.cleannoteapp.presentation.common

import androidx.lifecycle.ViewModel
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.models.ColorModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.models.enums.SortBy
import com.example.noteapp.cleannoteapp.models.enums.SortBy.*
import com.example.noteapp.cleannoteapp.models.enums.ViewBy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
) : ViewModel() {

    fun getViewByGrid(): ViewBy {
        return ViewBy.Grid
    }

    fun getCategoryOne(): ColorCategory {
        return ColorCategory.OPTION_ONE
    }

    fun getSortByModifiedTime(): SortBy {
        return MODIFIED_TIME
    }

    fun getCategoryDefault(): ColorCategory {
        return ColorCategory.DEFAULT
    }

    fun getCategoryAllNotes(): ColorCategory {
        return ColorCategory.ALL_NOTES
    }

    fun getColorCategoryItem(colorCategory: ColorCategory): ColorModel {
        return when (colorCategory) {
            ColorCategory.OPTION_ONE, ColorCategory.DEFAULT -> {
                ColorModel(
                    primaryColor = R.color.color_one_primary,
                    secondaryColor = R.color.color_one_secondary,
                    selectedItem = R.id.selected_one,
                    theme = R.style.Theme_CleanNoteApp_One
                )
            }
            ColorCategory.OPTION_TWO -> {
                ColorModel(
                    primaryColor = R.color.color_two_primary,
                    secondaryColor = R.color.color_two_secondary,
                    selectedItem = R.id.selected_two,
                    theme = R.style.Theme_CleanNoteApp_Two
                )
            }
            ColorCategory.OPTION_THREE -> {
                ColorModel(
                    primaryColor = R.color.color_three_primary,
                    secondaryColor = R.color.color_three_secondary,
                    selectedItem = R.id.selected_three,
                    theme = R.style.Theme_CleanNoteApp_Three
                )
            }
            ColorCategory.OPTION_FOUR -> {
                ColorModel(
                    primaryColor = R.color.color_four_primary,
                    secondaryColor = R.color.color_four_secondary,
                    selectedItem = R.id.selected_four,
                    theme = R.style.Theme_CleanNoteApp_Four
                )
            }
            ColorCategory.OPTION_FIVE -> {
                ColorModel(
                    primaryColor = R.color.color_five_primary,
                    secondaryColor = R.color.color_five_secondary,
                    selectedItem = R.id.selected_five,
                    theme = R.style.Theme_CleanNoteApp_Five
                )
            }
            ColorCategory.OPTION_SIX -> {
                ColorModel(
                    primaryColor = R.color.color_six_primary,
                    secondaryColor = R.color.color_six_secondary,
                    selectedItem = R.id.selected_six,
                    theme = R.style.Theme_CleanNoteApp_Six
                )
            }
            ColorCategory.OPTION_SEVEN -> {
                ColorModel(
                    primaryColor = R.color.color_seven_primary,
                    secondaryColor = R.color.color_seven_secondary,
                    selectedItem = R.id.selected_seven,
                    theme = R.style.Theme_CleanNoteApp_Seven
                )
            }
            ColorCategory.OPTION_EIGHT -> {
                ColorModel(
                    primaryColor = R.color.color_eight_primary,
                    secondaryColor = R.color.color_eight_secondary,
                    selectedItem = R.id.selected_eight,
                    theme = R.style.Theme_CleanNoteApp_Eight
                )
            }
            ColorCategory.ALL_NOTES -> {
                ColorModel(
                    primaryColor = R.drawable.all_category,
                    secondaryColor = R.color.white,
                    selectedItem = R.id.selected_all,
                    theme = R.style.Theme_CleanNoteApp_One
                )
            }
        }
    }

    fun getSortByID(sortBy: SortBy): Int {
        return when (sortBy) {
            MODIFIED_TIME -> R.id.sort_by_modified_time
            CREATED_TIME -> R.id.sort_by_created_time
            REMINDER_TIME -> R.id.sort_by_reminder_time
            COLOR -> R.id.sort_by_color
        }
    }

    fun getViewByID(view: ViewBy): Int {
        return when (view) {
            ViewBy.List -> R.id.view_by_list
            ViewBy.Grid -> R.id.view_by_grid
            ViewBy.Details -> R.id.view_by_details
            ViewBy.Default -> R.id.view_by_grid
        }
    }
}
package com.example.noteapp.cleannoteapp.presentation.common

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.models.ColorModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.util.PreferenceKeys
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
) : ViewModel() {
    fun getCategoryOne(): ColorCategory {
        return ColorCategory.OPTION_ONE
    }

    fun getCategoryTwo(): ColorCategory {
        return ColorCategory.OPTION_TWO
    }

    fun getCategoryThree(): ColorCategory {
        return ColorCategory.OPTION_THREE
    }

    fun getCategoryFour(): ColorCategory {
        return ColorCategory.OPTION_FOUR
    }

    fun getCategoryFive(): ColorCategory {
        return ColorCategory.OPTION_FIVE
    }

    fun getCategorySix(): ColorCategory {
        return ColorCategory.OPTION_SIX
    }

    fun getCategorySeven(): ColorCategory {
        return ColorCategory.OPTION_SEVEN
    }

    fun getCategoryEight(): ColorCategory {
        return ColorCategory.OPTION_EIGHT
    }

    fun getCategoryDefault(): ColorCategory {
        return ColorCategory.DEFAULT
    }

    fun getCategoryAllNotes(): ColorCategory {
        return ColorCategory.ALL_NOTES
    }

    fun getColorCategoryItem(colorCategory: ColorCategory): ColorModel {
        return when (colorCategory) {
            ColorCategory.OPTION_ONE -> {
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
            ColorCategory.DEFAULT -> {
                ColorModel(
                    primaryColor = R.color.color_one_primary,
                    secondaryColor = R.color.color_one_secondary,
                    selectedItem = R.id.selected_one,
                    theme = R.style.Theme_CleanNoteApp_One
                )
            }
            ColorCategory.ALL_NOTES -> {
                ColorModel(
                    primaryColor = R.color.white,
                    secondaryColor = R.color.white,
                    selectedItem = R.id.selected_all,
                    theme = R.style.Theme_CleanNoteApp_One
                )
            }
        }
    }
}
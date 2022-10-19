package com.example.noteapp.cleannoteapp.presentation.common

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.noteapp.cleannoteapp.R
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

    fun getSecondaryColor(colorCategory: ColorCategory): Int {
        return when (colorCategory) {
            ColorCategory.OPTION_ONE -> {
                R.color.color_one_primary
            }
            ColorCategory.OPTION_TWO -> {
                R.color.color_two_primary
            }
            ColorCategory.OPTION_THREE -> {
                R.color.color_three_primary
            }
            ColorCategory.OPTION_FOUR -> {
                R.color.color_four_primary
            }
            ColorCategory.OPTION_FIVE -> {
                R.color.color_five_primary
            }
            ColorCategory.OPTION_SIX -> {
                R.color.color_six_primary
            }
            ColorCategory.OPTION_SEVEN -> {
                R.color.color_seven_primary
            }
            ColorCategory.OPTION_EIGHT -> {
                R.color.color_eight_primary
            }
            ColorCategory.DEFAULT -> {
                R.color.color_one_primary
            }
        }
    }
}
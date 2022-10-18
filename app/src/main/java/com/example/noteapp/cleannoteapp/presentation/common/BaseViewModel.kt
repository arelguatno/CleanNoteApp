package com.example.noteapp.cleannoteapp.presentation.common

import androidx.lifecycle.ViewModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
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
}
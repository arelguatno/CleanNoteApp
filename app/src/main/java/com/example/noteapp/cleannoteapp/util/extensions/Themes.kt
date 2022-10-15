package com.example.noteapp.cleannoteapp.util.extensions

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory

fun View.setThemeOne() {
    this.setBackgroundResource(R.color.color_one_secondary)
}

fun View.setThemeTwo() {
    this.setBackgroundResource(R.color.color_two_secondary)
}

fun View.setThemeThree() {
    this.setBackgroundResource(R.color.color_three_secondary)
}

fun View.setThemeFour() {
    this.setBackgroundResource(R.color.color_four_secondary)
}

fun View.setThemeFive() {
    this.setBackgroundResource(R.color.color_five_secondary)
}

fun View.setThemeSix() {
    this.setBackgroundResource(R.color.color_six_secondary)
}

fun View.setThemeSeven() {
    this.setBackgroundResource(R.color.color_seven_secondary)
}

fun View.setThemeEight() {
    this.setBackgroundResource(R.color.color_eight_secondary)
}

fun Drawable.setColorTint(color: ColorCategory) {
    when (color) {
        ColorCategory.OPTION_ONE -> R.color.color_one_primary
        ColorCategory.OPTION_TWO -> R.color.color_two_primary
        ColorCategory.OPTION_THREE -> R.color.color_three_primary
        ColorCategory.OPTION_FOUR -> R.color.color_four_primary
        ColorCategory.OPTION_FIVE -> R.color.color_five_primary
        ColorCategory.OPTION_SIX -> R.color.color_six_primary
        ColorCategory.OPTION_SEVEN -> R.color.color_seven_primary
        ColorCategory.OPTION_EIGHT -> R.color.color_eight_primary
        else -> {}
    }
}



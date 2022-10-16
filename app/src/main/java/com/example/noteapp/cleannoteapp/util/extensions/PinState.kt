package com.example.noteapp.cleannoteapp.util.extensions

import android.content.res.Resources
import android.view.MenuItem
import androidx.core.content.res.ResourcesCompat
import com.example.noteapp.cleannoteapp.R

fun MenuItem.pinOnClick(resources: Resources) {
    val image = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_push_pin_24, null)
    this.icon = image
}

fun MenuItem.pinUnClick(resources: Resources) {
    val image = ResourcesCompat.getDrawable(resources, R.drawable.ic_outline_push_pin_24, null)
    this.icon = image
}
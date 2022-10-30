package com.example.noteapp.cleannoteapp.util.extensions

import android.content.res.Resources
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import com.example.noteapp.cleannoteapp.R

fun EditText.disableContentInteraction() {
    keyListener = null
    isFocusable = false
    isFocusableInTouchMode = false
    isCursorVisible = false
    clearFocus()
}

fun EditText.enableContentInteraction() {
    keyListener = EditText(context).keyListener
    isFocusable = true
    isFocusableInTouchMode = true
    isCursorVisible = true
    requestFocus()
    if (text != null) {
        setSelection(text.length)
    }
}

fun Toolbar.enableMultiSelection(resources: Resources) {
    menu.clear()
    inflateMenu(R.menu.multiselection_toolbar)
    title = ""
    navigationIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_close_24, null)
}

fun Toolbar.enableListViewToolbarState(resources: Resources) {
    menu.clear()
    navigationIcon = null
    inflateMenu(R.menu.list_fragment_menu)
    title = resources.getString(R.string.app_name)
}



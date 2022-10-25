package com.example.noteapp.cleannoteapp.util.extensions

import android.view.View
import android.widget.EditText
import androidx.core.content.ContextCompat

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
    if(text != null){
        setSelection(text.length)
    }
}

fun View.changeColor(newColor: Int) {
    setBackgroundColor(
        ContextCompat.getColor(
            context,
            newColor
        )
    )
}
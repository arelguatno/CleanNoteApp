package com.example.noteapp.cleannoteapp.util.extensions

import android.widget.EditText

fun EditText.disableContentInteraction() {
   // keyListener = null
    // isFocusable = false
    //isFocusableInTouchMode = false
//    isCursorVisible = false
//    clearFocus()
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
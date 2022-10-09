package com.example.noteapp.cleannoteapp.util

import android.util.Log
import com.example.noteapp.cleannoteapp.util.Constants.DEBUG
import com.example.noteapp.cleannoteapp.util.Constants.TAG

fun printLogD(className: String?, message: String) {
    if (DEBUG) {
        Log.d(TAG, "$className: $message")
    } else if (DEBUG) {
        println("$className: $message")
    }
}
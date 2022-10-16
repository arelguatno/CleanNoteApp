package com.example.noteapp.cleannoteapp.util.extensions

import android.content.SharedPreferences

fun SharedPreferences.save(key: String, value: String, force: Boolean = false){
    val editor = edit().putString(key,value)
    if (force) editor.commit() else editor.apply()
}

fun SharedPreferences.save(key: String, value: Int, force: Boolean = false){
    val editor = edit().putInt(key,value)
    if (force) editor.commit() else editor.apply()
}

fun SharedPreferences.save(key: String, value: Boolean, force: Boolean = false){
    val editor = edit().putBoolean(key,value)
    if (force) editor.commit() else editor.apply()
}

fun SharedPreferences.save(key: String, value: Long, force: Boolean = false){
    val editor = edit().putLong(key,value)
    if (force) editor.commit() else editor.apply()
}

fun SharedPreferences.save(key: String, value: Float, force: Boolean = false){
    val editor = edit().putFloat(key,value)
    if (force) editor.commit() else editor.apply()
}

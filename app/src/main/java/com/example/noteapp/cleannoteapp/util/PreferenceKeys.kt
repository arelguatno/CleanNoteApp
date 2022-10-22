package com.example.noteapp.cleannoteapp.util

class PreferenceKeys {

    companion object {
        // Shared Preference Files:
        const val NOTE_PREFERENCES: String = "com.example.noteapp.cleannoteapp.models"

        // Shared Preference Keys
        const val USER_DYNAMIC_THEME_PREFERENCE: String =
            "$NOTE_PREFERENCES.USER_DYNAMIC_THEME_PREFERENCE"

        // Settings - Default Color
        const val SETTINGS_DEFAULT_COLOR: String = "$NOTE_PREFERENCES.SETTINGS_DEFAULT_COLOR"

        //List View - Color Picker
        const val LIST_VIEW_COLOR_THEME: String = "$NOTE_PREFERENCES.LIST_VIEW_COLOR_THEME"

        //List View - Sort By
        const val LIST_VIEW_SORT_BY: String = "$NOTE_PREFERENCES.LIST_VIEW_SORT_BY"
    }
}
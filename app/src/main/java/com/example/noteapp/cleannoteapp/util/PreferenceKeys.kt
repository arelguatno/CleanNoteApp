package com.example.noteapp.cleannoteapp.util

import com.example.noteapp.cleannoteapp.BuildConfig

class PreferenceKeys {

    companion object {
        // Shared Preference Files:
        const val NOTE_PREFERENCES: String = BuildConfig.APPLICATION_ID

        // Shared Preference Keys
        const val USER_DYNAMIC_THEME_PREFERENCE: String =
            "$NOTE_PREFERENCES.USER_DYNAMIC_THEME_PREFERENCE"

        // Settings - Default Color
        const val SETTINGS_DEFAULT_COLOR: String = "$NOTE_PREFERENCES.SETTINGS_DEFAULT_COLOR"

        //List View - Color Picker
        const val LIST_VIEW_COLOR_THEME: String = "$NOTE_PREFERENCES.LIST_VIEW_COLOR_THEME"

        //List View - Sort By
        const val LIST_VIEW_SORT_BY: String = "$NOTE_PREFERENCES.LIST_VIEW_SORT_BY"

        //List View - View By
        const val LIST_VIEW_VIEW_BY: String = "$NOTE_PREFERENCES.LIST_VIEW_VIEW_BY"

        const val ADD_UPDATE_RESULT: String = "$NOTE_PREFERENCES.ADD_UPDATE_RESULT"
        const val ADD_UPDATE_NODE_MODEL: String = "$NOTE_PREFERENCES.ADD_UPDATE_NODE_MODEL"

        const val BIN_ARCHIVE_VIEW: String = "$NOTE_PREFERENCES.BIN_ARCHIVE_VIEW"
    }
}
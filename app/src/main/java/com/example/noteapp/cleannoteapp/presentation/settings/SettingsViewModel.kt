package com.example.noteapp.cleannoteapp.presentation.settings

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.common.BaseViewModel
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionManager
import com.example.noteapp.cleannoteapp.util.PreferenceKeys
import com.example.noteapp.cleannoteapp.util.extensions.save
import com.example.noteapp.cleannoteapp.util.printLogD
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val sharedPref: SharedPreferences
) : BaseViewModel() {
    private val noteInteractionManager: NoteInteractionManager = NoteInteractionManager()

    val themeSelectedInteraction: LiveData<ColorCategory>
        get() = noteInteractionManager.themeSelected

    fun setThemeSelected(color: ColorCategory) {
        noteInteractionManager.setThemeSelected(color)
    }

    internal fun loadDefaultColor() {
        for (i in ColorCategory.values()) {
            if (getColorSettingsMenu().equals(i.toString())) {
                setThemeSelected(i)
            }
        }
    }

    private fun getColorSettingsMenu(): String? {
        return sharedPref.getString(
            PreferenceKeys.SETTINGS_DEFAULT_COLOR,
            getCategoryOne().toString()
        )
    }

    fun saveDefaultColor(color: String){
        sharedPref.save(PreferenceKeys.SETTINGS_DEFAULT_COLOR, color)
    }
}
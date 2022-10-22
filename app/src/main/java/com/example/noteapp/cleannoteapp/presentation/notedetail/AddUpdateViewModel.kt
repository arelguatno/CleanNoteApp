package com.example.noteapp.cleannoteapp.presentation.notedetail

/**/
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.common.BaseViewModel
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionManager
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState
import com.example.noteapp.cleannoteapp.util.PreferenceKeys
import com.example.noteapp.cleannoteapp.util.PreferenceKeys.Companion.USER_DYNAMIC_THEME_PREFERENCE
import com.example.noteapp.cleannoteapp.util.extensions.save
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddUpdateViewModel @Inject constructor(
    private val editor: SharedPreferences.Editor,
    private val sharedPreferences: SharedPreferences
) : BaseViewModel() {
    private val noteInteractionManager: NoteInteractionManager = NoteInteractionManager()

    val noteTitleInteractionState: LiveData<NoteInteractionState>
        get() = noteInteractionManager.noteTitleState

    val noteBodyInteractionState: LiveData<NoteInteractionState>
        get() = noteInteractionManager.noteBodyState

    val pinnedInteractionState: LiveData<NoteInteractionState>
        get() = noteInteractionManager.pinnedState

    val themeSelectedInteraction: LiveData<ColorCategory>
        get() = noteInteractionManager.themeSelected

    val setThemeState: LiveData<NoteInteractionState>
        get() = noteInteractionManager.themeState

    val currentInteractionDate: LiveData<Date>
        get() = noteInteractionManager.currentDate

    fun isEditingBody() = noteInteractionManager.isEditingBody()

    fun isEditingTitle() = noteInteractionManager.isEditingTitle()

    fun isEditingPin() = noteInteractionManager.isEditingPin()

    fun checkEditState() = noteInteractionManager.checkEditState()

    fun exitEditState() = noteInteractionManager.exitEditState()

    fun setNoteInteractionBodyState(state: NoteInteractionState) {
        noteInteractionManager.setNewNoteBodyState(state)
    }

    fun setNoteInteractionTitleState(state: NoteInteractionState) {
        noteInteractionManager.setNewNoteTitleState(state)
    }

    fun setThemeSelected(color: ColorCategory) {
        noteInteractionManager.setThemeSelected(color)
    }

    fun setThemeState(state: NoteInteractionState) {
        noteInteractionManager.setThemeState(state)
    }

    fun setPinnedState(state: NoteInteractionState) {
        noteInteractionManager.setPinnedState(state)
    }

    fun setCurrentDate(date: Date) {
        noteInteractionManager.setCurrentDate(date)
    }

    fun storeThemeSelected(category: ColorCategory) {
        sharedPreferences.save(USER_DYNAMIC_THEME_PREFERENCE, category.toString())
    }

    fun getThemeColorForActivity(value: String): Int {
        return when (sharedPreferences.getString(value, getCategoryDefault().toString())) {
            ColorCategory.OPTION_ONE.toString() -> R.style.Theme_CleanNoteApp_One
            ColorCategory.OPTION_TWO.toString() -> R.style.Theme_CleanNoteApp_Two
            ColorCategory.OPTION_THREE.toString() -> R.style.Theme_CleanNoteApp_Three
            ColorCategory.OPTION_FOUR.toString() -> R.style.Theme_CleanNoteApp_Four
            ColorCategory.OPTION_FIVE.toString() -> R.style.Theme_CleanNoteApp_Five
            ColorCategory.OPTION_SIX.toString() -> R.style.Theme_CleanNoteApp_Six
            ColorCategory.OPTION_SEVEN.toString() -> R.style.Theme_CleanNoteApp_Seven
            ColorCategory.OPTION_EIGHT.toString() -> R.style.Theme_CleanNoteApp_Eight
            else -> {
                R.style.Theme_CleanNoteApp_One
            }
        }
    }

    internal fun loadDefaultColor() {
        for (i in ColorCategory.values()) {
            if (getColorSettingsMenu().equals(i.toString())) {
                setThemeSelected(i)
            }
        }
    }

    private fun getColorSettingsMenu(): String? {
        return sharedPreferences.getString(
            PreferenceKeys.SETTINGS_DEFAULT_COLOR,
            getCategoryDefault().toString()
        )
    }

    private fun getColorSharedPrefFromFragment(value: String): String? {
        return sharedPreferences.getString(
            value,
            getCategoryDefault().toString()
        )
    }
}
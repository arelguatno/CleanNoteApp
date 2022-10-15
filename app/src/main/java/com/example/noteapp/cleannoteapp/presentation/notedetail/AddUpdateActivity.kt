package com.example.noteapp.cleannoteapp.presentation.notedetail

import android.content.SharedPreferences
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.presentation.common.BaseActivity
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.DefaultState
import com.example.noteapp.cleannoteapp.presentation.notedetail.state.NoteInteractionState.EditState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUpdateActivity : BaseActivity() {
    private lateinit var sharedPref: SharedPreferences

    companion object {
        const val DETAIL_FRAGMENT =
            "com.example.noteapp.cleannoteapp.presentation.notedetail.detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (mainViewModel.setThemeState.value) {
            EditState -> setTheme(setTheme())
            DefaultState -> setTheme(R.style.Theme_CleanNoteApp_One) //TODO default theme. Can be fix in settings
            else -> {}
        }

        setContentView(R.layout.activity_add_update)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setTheme(): Int {
        sharedPref = this.getPreferences(MODE_PRIVATE)
        return when (sharedPref.getInt(this.getString(R.string.color_id), 0)) {
            1 -> R.style.Theme_CleanNoteApp_One
            2 -> R.style.Theme_CleanNoteApp_Two
            3 -> R.style.Theme_CleanNoteApp_Three
            4 -> R.style.Theme_CleanNoteApp_Four
            5 -> R.style.Theme_CleanNoteApp_Five
            6 -> R.style.Theme_CleanNoteApp_Six
            7 -> R.style.Theme_CleanNoteApp_Seven
            8 -> R.style.Theme_CleanNoteApp_Eight
            else -> R.style.Theme_CleanNoteApp
        }
    }
}
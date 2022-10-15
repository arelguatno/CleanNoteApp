package com.example.noteapp.cleannoteapp.presentation.notedetail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.noteapp.cleannoteapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUpdateActivity : AppCompatActivity() {
    companion object{
        const val DETAIL_FRAGMENT = "com.example.noteapp.cleannoteapp.presentation.notedetail.detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(setTheme())
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setTheme(): Int {
        val sharedPref = this.getPreferences(MODE_PRIVATE)
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
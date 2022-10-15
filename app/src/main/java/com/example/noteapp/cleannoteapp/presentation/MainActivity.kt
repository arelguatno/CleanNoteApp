package com.example.noteapp.cleannoteapp.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.ActivityMainBinding
import com.example.noteapp.cleannoteapp.presentation.common.BaseActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(getPremiumUser())
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupWithNavController(binding.bottomNavigationView, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun getPremiumUser(): Int {
        val sharedPref = this.getSharedPreferences(
            this.getString(R.string.color_id),
            Context.MODE_PRIVATE
        )
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
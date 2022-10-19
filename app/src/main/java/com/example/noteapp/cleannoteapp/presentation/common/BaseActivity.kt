package com.example.noteapp.cleannoteapp.presentation.common

import android.content.SharedPreferences
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.noteapp.cleannoteapp.presentation.notedetail.AddUpdateViewModel
import com.example.noteapp.cleannoteapp.util.printLogD
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity() : AppCompatActivity() {
    @Inject
    lateinit var sharedPref: SharedPreferences

    @Inject
    lateinit var sharedPrefEditor: SharedPreferences.Editor

    val mainViewModel: AddUpdateViewModel by viewModels()
}
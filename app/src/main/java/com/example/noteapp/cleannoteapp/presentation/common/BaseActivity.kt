package com.example.noteapp.cleannoteapp.presentation.common

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.noteapp.cleannoteapp.databinding.ActivityMainBinding
import com.example.noteapp.cleannoteapp.presentation.notedetail.AddUpdateViewModel
import com.example.noteapp.cleannoteapp.util.printLogD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity() : AppCompatActivity() {
    val mainViewModel: AddUpdateViewModel by viewModels()

    override fun onDestroy() {
        printLogD("BaseActivity","onDestroy")
        super.onDestroy()
    }
}
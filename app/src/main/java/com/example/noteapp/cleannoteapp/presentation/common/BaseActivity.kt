package com.example.noteapp.cleannoteapp.presentation.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.noteapp.cleannoteapp.databinding.ActivityMainBinding
import com.example.noteapp.cleannoteapp.util.printLogD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity() : AppCompatActivity() {

    override fun onDestroy() {
        printLogD("BaseActivity","onDestroy")
        super.onDestroy()
    }
}
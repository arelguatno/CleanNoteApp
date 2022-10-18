package com.example.noteapp.cleannoteapp.presentation.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.SettingsFeedbackWidgetBinding
import com.example.noteapp.cleannoteapp.databinding.SettingsSettingsBinding
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment

class SettingsFragment : BaseFragment() {
    private lateinit var binding: SettingsSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SettingsSettingsBinding.inflate(layoutInflater)
        return binding.root
    }
}
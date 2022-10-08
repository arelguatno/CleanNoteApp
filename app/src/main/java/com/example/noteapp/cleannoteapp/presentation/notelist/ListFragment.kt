package com.example.noteapp.cleannoteapp.presentation.notelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.databinding.FragmentListBinding


class ListFragment : BaseFragment() {
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(layoutInflater)
        menuInit()
        return binding.root
    }

    private fun menuInit() {
        binding.appBar.inflateMenu(R.menu.list_fragment_menu)
      //  binding.appBar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
    }
}
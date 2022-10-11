package com.example.noteapp.cleannoteapp.presentation.notedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.noteapp.cleannoteapp.databinding.FragmentAddUpdateBinding
import com.example.noteapp.cleannoteapp.models.NoteModel
import com.example.noteapp.cleannoteapp.models.enums.ColorCategory
import com.example.noteapp.cleannoteapp.presentation.MainActivity
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.util.extensions.hideKeyboard
import com.example.noteapp.cleannoteapp.util.extensions.showKeyboard
import com.example.noteapp.cleannoteapp.util.printLogD
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUpdateFragment : BaseFragment() {
    private lateinit var binding: FragmentAddUpdateBinding
    private lateinit var activityMain: MainActivity
    private val viewModel: DetailViewModel by viewModels()
    private val className = this.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        backButtonOnClick()
        binding.addTextLayout.txtBody.showKeyboard()

        val newData = NoteModel(header = "Sample1", date = "null", body = "null", category = ColorCategory.OPTION_ONE)
        viewModel.insertRecord(newData)
        printLogD(className, "onStart")
    }

    private fun backButtonOnClick() {
        binding.appBar.setNavigationOnClickListener {
            it.hideKeyboard()
            findNavController().navigateUp()
        }
    }
}
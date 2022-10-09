package com.example.noteapp.cleannoteapp.presentation.notedetail

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import com.example.noteapp.cleannoteapp.R
import com.example.noteapp.cleannoteapp.databinding.FragmentAddUpdateBinding
import com.example.noteapp.cleannoteapp.presentation.MainActivity
import com.example.noteapp.cleannoteapp.presentation.common.BaseFragment
import com.example.noteapp.cleannoteapp.util.extensions.hideKeyboard
import com.example.noteapp.cleannoteapp.util.extensions.showKeyboard
import com.example.noteapp.cleannoteapp.util.printLogD


class AddUpdateFragment : BaseFragment() {
    private lateinit var binding: FragmentAddUpdateBinding
    private lateinit var activityMain: MainActivity
    private val className = this.javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddUpdateBinding.inflate(layoutInflater)
        printLogD(className, "This is me")
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        backButtonOnClick()
        binding.addTextLayout.txtBody.showKeyboard()
    }

    private fun backButtonOnClick() {
        binding.appBar.setNavigationOnClickListener {
            it.hideKeyboard()
            findNavController().navigateUp()
        }
    }
}